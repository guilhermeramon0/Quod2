
package br.com.fiap.quodson.repository

import br.com.fiap.quodson.model.ValidationRecord
import br.com.fiap.quodson.model.ValidationType
import br.com.fiap.quodson.model.ValidationResult
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface ValidationRepository : MongoRepository<ValidationRecord, String> {
    
    fun findByType(type: ValidationType): List<ValidationRecord>
    
    fun findByResult(result: ValidationResult): List<ValidationRecord>
    
    fun findByTypeAndResult(type: ValidationType, result: ValidationResult): List<ValidationRecord>
    
    fun findByCreatedAtBetween(start: LocalDateTime, end: LocalDateTime): List<ValidationRecord>
    
    fun findByNotificationSent(notificationSent: Boolean): List<ValidationRecord>
}