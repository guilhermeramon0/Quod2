package br.com.fiap.quodson.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class AppConfig {

    /**
     * Configura o RestTemplate para chamadas HTTP
     */
    @Bean
    fun restTemplate(): RestTemplate {
        val factory = SimpleClientHttpRequestFactory()
        factory.setConnectTimeout(5000) // 5 segundos
        factory.setReadTimeout(5000) // 5 segundos
        return RestTemplate(factory)
    }

    /**
     * Configuração CORS para permitir chamadas do frontend
     */
    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        // Permitir todas as origens - em produção, isso seria mais restritivo
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")

        source.registerCorsConfiguration("/**", config)
        return CorsFilter(source)
    }
}

// Se precisar de configurações específicas do MongoDB além das propriedades no application.yml
/*
@Configuration
class MongoConfig : AbstractMongoClientConfiguration() {

    @Value("\${spring.data.mongodb.uri}")
    private lateinit var mongoUri: String

    @Value("\${spring.data.mongodb.database}")
    private lateinit var mongoDatabase: String

    override fun getDatabaseName(): String {
        return mongoDatabase
    }

    override fun mongoClient(): MongoClient {
        return MongoClients.create(mongoUri)
    }
}
*/