package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class Partido(
    @SerializedName("id_partido")
    val id_partido: Int,
    @SerializedName("nombre_equipo_local")
    val nombreEquipoLocal: String,
    @SerializedName("nombre_equipo_visita")
    val nombreEquipoVisita: String,
    @SerializedName("goles_local")
    val goles_local: Int,
    @SerializedName("goles_visita")
    val goles_visita: Int,
    @SerializedName("fecha_partido")
    val fecha_partido: String,
    @SerializedName("estadio")
    val estadio: String
)
