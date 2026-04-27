package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Jugador(
    @SerializedName("id_jugador")
    val id_jugador: Int,
    @SerializedName("nombre")
    val nombre: String? = null,
    @SerializedName("posicion")
    val posicion: String? = null,
    @SerializedName("dorsal")
    val dorsal: Int? = 0,
    @SerializedName("fecha_nacimiento")
    val fechaNacimiento: String? = null,
    @SerializedName("nacionalidad")
    val nacionalidad: String? = null,
    @SerializedName("nombre_equipo")
    val nombreEquipo: String? = null,
    @SerializedName("goles")
    val goles: Int? = 0
)
