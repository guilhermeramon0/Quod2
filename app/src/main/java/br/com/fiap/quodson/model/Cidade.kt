package br.com.fiap.quodson.model

import com.google.gson.annotations.SerializedName

class Cidade {

    val nome: String = "Cidade"
    @SerializedName("UF")
    val estado: Estado = Estado()

}