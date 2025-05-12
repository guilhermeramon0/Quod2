package br.com.fiap.quodson.service

import br.com.fiap.quodson.model.*
import br.com.fiap.quodson.repository.ValidationRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class FacialBiometryService(
    private val validationRepository: ValidationRepository,
    private val notificationService: NotificationService
) {
    private val logger = LoggerFactory.getLogger(FacialBiometryService::class.java)

    @Value("\${quodson.validation.min-face-confidence}")
    private var minConfidence: Double = 0.75

    /**
     * Valida biometria facial
     * Simula detecção de fraudes como:
     * - Foto de foto
     * - Deepfake
     * - Máscaras
     */
    fun validateFace(imageFile: MultipartFile, deviceInfo: DeviceInfo): BiometricResponseDTO {
        logger.info("Validando biometria facial")

        if (imageFile.isEmpty) {
            return BiometricResponseDTO(
                valid = false,
                confidence = 0.0,
                message = "Imagem não fornecida"
            )
        }

        if (!isValidImageFormat(imageFile)) {
            return BiometricResponseDTO(
                valid = false,
                confidence = 0.0,
                message = "Formato de imagem inválido. Aceitos: JPEG, PNG"
            )
        }

        // Simulação de análise de imagem e detecção de fraudes
        val isFraud = shouldSimulateFraud()
        val confidence = calculateConfidence(isFraud)
        val isValid = confidence >= minConfidence && !isFraud

        val fraudType = if (isFraud) {
            when (Random.nextInt(3)) {
                0 -> "Foto de foto detectada"
                1 -> "Possível deepfake"
                else -> "Máscara ou outra obstrução detectada"
            }
        } else null

        // Armazenar registro no banco de dados
        val record = ValidationRecord(
            type = ValidationType.FACE,
            result = if (isValid) ValidationResult.SUCCESS else ValidationResult.FRAUD_DETECTED,
            confidence = confidence,
            deviceInfo = deviceInfo,
            fraudDetails = fraudType,
            createdAt = LocalDateTime.now()
        )

        val savedRecord = validationRepository.save(record)

        // Se for fraude, notificar o sistema interno
        if (isFraud) {
            val notification = notificationService.sendFraudNotification(savedRecord)
            if (notification != null) {
                // Atualizar o registro com a notificação enviada
                validationRepository.save(savedRecord.copy(
                    notificationSent = true,
                    notificationId = notification.notificationId
                ))
            }
        }

        return BiometricResponseDTO(
            valid = isValid,
            confidence = confidence,
            message = fraudType ?: "Biometria facial válida"
        )
    }

    private fun isValidImageFormat(file: MultipartFile): Boolean {
        val contentType = file.contentType ?: return false
        return contentType == "image/jpeg" ||
                contentType == "image/png" ||
                contentType == "image/jpg"
    }

    private fun shouldSimulateFraud(): Boolean {
        // Simula aproximadamente 20% de chances de fraude
        return Random.nextDouble() < 0.2
    }

    private fun calculateConfidence(isFraud: Boolean): Double {
        return if (isFraud) {
            // Valor de confiança menor para casos de fraude
            Random.nextDouble(0.3, 0.74)
        } else {
            // Valor de confiança maior para casos válidos
            Random.nextDouble(0.75, 0.99)
        }
    }
}

@Service
class FingerprintBiometryService(
    private val validationRepository: ValidationRepository,
    private val notificationService: NotificationService
) {
    private val logger = LoggerFactory.getLogger(FingerprintBiometryService::class.java)

    @Value("\${quodson.validation.min-fingerprint-confidence}")
    private var minConfidence: Double = 0.80

    /**
     * Valida biometria digital (impressão digital)
     */
    fun validateFingerprint(imageFile: MultipartFile, deviceInfo: DeviceInfo): BiometricResponseDTO {
        logger.info("Validando impressão digital")

        if (imageFile.isEmpty) {
            return BiometricResponseDTO(
                valid = false,
                confidence = 0.0,
                message = "Imagem não fornecida"
            )
        }

        if (!isValidImageFormat(imageFile)) {
            return BiometricResponseDTO(
                valid = false,
                confidence = 0.0,
                message = "Formato de imagem inválido. Aceitos: JPEG, PNG"
            )
        }

        // Simulação de análise e validação
        val isFraud = shouldSimulateFraud()
        val confidence = calculateConfidence(isFraud)
        val isValid = confidence >= minConfidence && !isFraud

        val fraudType = if (isFraud) {
            when (Random.nextInt(3)) {
                0 -> "Dedo artificial detectado"
                1 -> "Tentativa de reutilização de impressão"
                else -> "Qualidade insuficiente para validação"
            }
        } else null

        // Armazenar registro no banco de dados
        val record = ValidationRecord(
            type = ValidationType.FINGERPRINT,
            result = if (isValid) ValidationResult.SUCCESS else ValidationResult.FRAUD_DETECTED,
            confidence = confidence,
            deviceInfo = deviceInfo,
            fraudDetails = fraudType,
            createdAt = LocalDateTime.now()
        )

        val savedRecord = validationRepository.save(record)

        // Se for fraude, notificar o sistema interno
        if (isFraud) {
            val notification = notificationService.sendFraudNotification(savedRecord)
            if (notification != null) {
                validationRepository.save(savedRecord.copy(
                    notificationSent = true,
                    notificationId = notification.notificationId
                ))
            }
        }

        return BiometricResponseDTO(
            valid = isValid,
            confidence = confidence,
            message = fraudType ?: "Impressão digital válida"
        )
    }

    private fun isValidImageFormat(file: MultipartFile): Boolean {
        val contentType = file.contentType ?: return false
        return contentType == "image/jpeg" ||
                contentType == "image/png" ||
                contentType == "image/jpg"
    }

    private fun shouldSimulateFraud(): Boolean {
        // Simula aproximadamente 15% de chances de fraude
        return Random.nextDouble() < 0.15
    }

    private fun calculateConfidence(isFraud: Boolean): Double {
        return if (isFraud) {
            Random.nextDouble(0.4, 0.79)
        } else {
            Random.nextDouble(0.80, 0.99)
        }
    }
}