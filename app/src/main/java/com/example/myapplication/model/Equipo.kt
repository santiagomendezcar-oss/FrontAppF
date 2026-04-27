package com.example.myapplication.model


import com.google.gson.annotations.SerializedName

data class Equipo(
    @SerializedName("id_equipo")
    val id_equipo: Int,
    @SerializedName("nombre")
    val nombre: String? = null,
    @SerializedName("ciudad")
    val ciudad: String? = null,
    @SerializedName("fundacion")
    val fundacion: String? = null,
    @SerializedName("estadio", alternate = ["nombre_estadio", "sede", "stadium", "estadio_nombre"])
    val estadio: String? = null,
    @SerializedName("entrenador", alternate = ["nombre_entrenador", "tecnico", "director_tecnico", "coach", "entrenador_nombre"])
    val entrenador: String? = null
)