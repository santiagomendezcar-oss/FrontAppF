package com.example.myapplication.model

import com.google.gson.annotations.SerializedName

data class ResulPartido(
    val id_partido: Int,
    val fecha_partido: String,
    val estadio: String,
    
    @SerializedName("nombreEquipoLocal") 
    val nombreEquipoLocal: String,
    
    @SerializedName("nombreEquipoVisita") 
    val nombreEquipoVisita: String,
    
    val goles_local: Int,
    val goles_visita: Int
)
