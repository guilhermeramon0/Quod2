package br.com.fiap.quodson.service

import br.com.fiap.quodson.model.NotificationResponseDTO
import br.com.fiap.quodson.model.ValidationRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.util.UUID

@Service
class NotificationService(
    private val restTemplate: RestTemplate
) {
    private val logger = LoggerFactory.getLogger(NotificationService::class.java)

    @Value("\${quodson.notification.url}")
    private lateinit var notificationUrl: String

    @Value("\${quodson.notification.enabled}")
    private var notificationEnabled: Boolean = true

    /**
     * Envia notificação para o sistema interno da QUOD quando fraude for detectada
     */
    fun sendFraudNotification(validationRecord: ValidationRecord): NotificationResponseDTO? {
        if (!notificationEnabled) {
            logger.info("Notificações desabilitadas. Ignorando notificação de fraude.")
            return null
        }

        try {
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_JSON

            val payload = mapOf(
                "validationId" to validationRecord.id,
                "type" to validationRecord.type,
                "result" to validationRecord.result,
                "timestamp" to validationRecord.createdAt,
                "fraudDetails" to validationRecord.fraudDetails
            )

            val request = HttpEntity(payload, headers)
            val response = restTemplate.postForObject(notificationUrl, request, NotificationResponseDTO::class.java)

            logger.info("Notificação de fraude enviada com sucesso: ${response?.notificationId}")
            return response

        } catch (e: Exception) {
            logger.error("Erro ao enviar notificação de fraude: ${e.message}")
            return NotificationResponseDTO(
                notificationId = UUID.randomUUID().toString(),
                status = "ERROR: ${e.message}"
            )
        }
    }
}