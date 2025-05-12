package br.com.fiap.quodson.service

import br.com.fiap.quodson.dto.DocumentResponseDTO
import br.com.fiap.quodson.model.*
import br.com.fiap.quodson.repository.ValidationRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class DocumentService(
        private val validationRepository: ValidationRepository,
        private val notificationService: NotificationService
) {
    private val logger = LoggerFactory.getLogger(DocumentService::class.java)

    @Value("\${quodson.validation.min-document-confidence}")
    private var minConfidence: Double = 0.85

    /**
     * Valida documento (RG, CNH, etc)
     * Implementa análise avançada para detectar fraudes como:
     * - Adulteração digital
     * - Documentos falsos/forjados
     * - Inconsistências nos dados
     */
    fun validarDocumento(file: MultipartFile, deviceInfo: DeviceInfo? = null): DocumentResponseDTO {
        logger.info("Validando documento: ${file.originalFilename}")

        if (file.isEmpty) {
            return DocumentResponseDTO(false, "Arquivo vazio")
        }

        if (!isValidDocumentImage(file)) {
            return DocumentResponseDTO(false, "Formato de arquivo inválido. Apenas imagens são permitidas.")
        }

        // Validações avançadas (simulação)
        val isFraud = shouldSimulateFraud()
        val confidence = calculateConfidence(isFraud)
        val isValid = confidence >= minConfidence && !isFraud

        val fraudDetails = if (isFraud) {
            when (Random.nextInt(4)) {
                0 -> "Adulteração digital detectada"
                1 -> "Possível documento falsificado"
                2 -> "Inconsistência nos dados do documento"
                else -> "Documento expirado ou inválido"
            }
        } else null

        // Dispositivo padrão se não fornecido
        val deviceInfoToUse = deviceInfo ?: DeviceInfo(
                manufacturer = "Unknown",
                model = "Unknown",
                captureTimestamp = LocalDateTime.now()
        )

        // Persistir o registro de validação
        val record = ValidationRecord(
                type = ValidationType.DOCUMENT,
                result = if (isValid) ValidationResult.SUCCESS else ValidationResult.FRAUD_DETECTED,
                confidence = confidence,
                deviceInfo = deviceInfoToUse,
                fraudDetails = fraudDetails,
                createdAt = LocalDateTime.now()
        )

        val savedRecord = validationRepository.save(record)

        // Notificar sistema interno se fraudulento
        if (isFraud) {
            val notificationResponse = notificationService.sendFraudNotification(savedRecord)
            if (notificationResponse != null) {
                validationRepository.save(savedRecord.copy(
                        notificationSent = true,
                        notificationId = notificationResponse.notificationId
                ))
            }
        }

        return DocumentResponseDTO(
                isValid,
                fraudDetails ?: "Documento válido"
        )
    }

    private fun isValidDocumentImage(file: MultipartFile): Boolean {
        val contentType = file.contentType ?: return false
        return contentType.startsWith("image/")
    }

    private fun shouldSimulateFraud(): Boolean {
        // Simula aproximadamente 25% de chances de fraude em documentos
        return Random.nextDouble() < 0.25
    }

    private fun calculateConfidence(isFraud: Boolean): Double {
        return if (isFraud) {
            Random.nextDouble(0.5, 0.84)
        } else {
            Random.nextDouble(0.85, 0.99)
        }
    }
}