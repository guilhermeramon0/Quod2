package br.com.fiap.quodson.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val URL = "https://servicodados.ibge.gov.br/api/docs/localidades/"

    private val DOCUMENTOSCOPIA_URL = "http://10.0.2.2:8080/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitDocumentoscopia = Retrofit.Builder()
        .baseUrl(DOCUMENTOSCOPIA_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getLocalidadeService(): LocalidadeService {
        return retrofitFactory.create(LocalidadeService::class.java)
    }

    fun getDocumentoscopiaService(): DocumentoscopiaService {
        return retrofitDocumentoscopia.create(DocumentoscopiaService::class.java)
    }
}