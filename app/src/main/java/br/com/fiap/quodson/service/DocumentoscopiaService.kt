package br.com.fiap.quodson.service

import br.com.fiap.quodson.model.DocumentoRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface DocumentoscopiaService {

    @Headers("Content-Type: application/json")
    @POST("/api/documentoscopia/analisar") // Ajuste para o endpoint real
    fun analisarDocumento(@Body request: DocumentoRequest): Call<Boolean>
}
