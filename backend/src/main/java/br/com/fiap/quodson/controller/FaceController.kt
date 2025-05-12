package br.com.fiap.quodson.controller

import br.com.fiap.quodson.model.BiometricResponseDTO
import br.com.fiap.quodson.model.DeviceInfo
import br.com.fiap.quodson.model.Location
import br.com.fiap.quodson.service.FacialBiometryService
import br.com.fiap.quodson.service.FingerprintBiometryService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/biometria")
class BiometryController(
    private val facialBiometryService: FacialBiometryService,
    private val fingerprintBiometryService: FingerprintBiometryService
) {

    @PostMapping(value = "/facial", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    fun validarBiometriaFacial(
        @RequestPart file: MultipartFile,
        @RequestParam(required = false) manufacturer: String?,
        @RequestParam(required = false) model: String?,
        @RequestParam(required = false) osVersion: String?,
        @RequestParam(required = false) appVersion: String?,
        @RequestParam(required = false) latitude: Double?,
        @RequestParam(required = false) longitude: Double?
    ): ResponseEntity<BiometricResponseDTO> {

        val deviceInfo = DeviceInfo(
            manufacturer = manufacturer,
            model = model,
            osVersion = osVersion,
            appVersion = appVersion,
            location = if (latitude != null && longitude != null) Location(latitude, longitude) else null,
            captureTimestamp = LocalDateTime.now()
        )

        val response = facialBiometryService.validateFace(file, deviceInfo)
        return ResponseEntity.ok(response)
    }

    @PostMapping(value = "/digital", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    fun validarBiometriaDigital(
        @RequestPart file: MultipartFile,
        @RequestParam(required = false) manufacturer: String?,
        @RequestParam(required = false) model: String?,
        @RequestParam(required = false) osVersion: String?,
        @RequestParam(required = false) appVersion: String?,
        @RequestParam(required = false) latitude: Double?,
        @RequestParam(required = false) longitude: Double?
    ): ResponseEntity<BiometricResponseDTO> {

        val deviceInfo = DeviceInfo(
            manufacturer = manufacturer,
            model = model,
            osVersion = osVersion,
            appVersion = appVersion,
            location = if (latitude != null && longitude != null) Location(latitude, longitude) else null,
            captureTimestamp = LocalDateTime.now()
        )

        val response = fingerprintBiometryService.validateFingerprint(file, deviceInfo)
        return ResponseEntity.ok(response)
    }
}