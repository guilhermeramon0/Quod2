package br.com.fiap.quodson.controller

import br.com.fiap.quodson.dto.DocumentResponseDTO
import br.com.fiap.quodson.model.DeviceInfo
import br.com.fiap.quodson.model.Location
import br.com.fiap.quodson.service.DocumentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/documentos")
class DocumentController {

    @Autowired
    private lateinit var documentService: DocumentService

    @PostMapping(value = "/validar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    fun validarDocumento(
            @RequestPart file: MultipartFile,
            @RequestParam(required = false) manufacturer: String?,
            @RequestParam(required = false) model: String?,
            @RequestParam(required = false) osVersion: String?,
            @RequestParam(required = false) appVersion: String?,
            @RequestParam(required = false) latitude: Double?,
            @RequestParam(required = false) longitude: Double?
    ): ResponseEntity<DocumentResponseDTO> {

        val deviceInfo = DeviceInfo(
                manufacturer = manufacturer,
                model = model,
                osVersion = osVersion,
                appVersion = appVersion,
                location = if (latitude != null && longitude != null) Location(latitude, longitude) else null,
                captureTimestamp = LocalDateTime.now()
        )

        return ResponseEntity.ok(documentService.validarDocumento(file, deviceInfo))
    }
}