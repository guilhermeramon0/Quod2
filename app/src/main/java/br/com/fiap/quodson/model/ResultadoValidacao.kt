package br.com.fiap.quodson.model

data class ResultadoValidacao(
    val valido: Boolean,
    val motivo: String? = null
)
