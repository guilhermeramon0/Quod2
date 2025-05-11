package br.com.fiap.quodson.model

data class DocumentoRequest(
    val imagemBase64: String,
    val tipoDocumento: String
)
