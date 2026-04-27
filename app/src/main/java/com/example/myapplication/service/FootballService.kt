package com.example.myapplication.service

import com.example.myapplication.model.Equipo
import com.example.myapplication.model.Jugador
import com.example.myapplication.model.ResulPartido

interface FootballService {
    suspend fun fetchJugadoresByEquipo(equipoId: Int): List<Jugador>
    suspend fun fetchJugadoresConMasGoles(minGoles: Int): List<Jugador>
    suspend fun fetchResultadosPartidos(): List<ResulPartido>
    suspend fun fetchTotalGolesEquipo(equipoId: Int): Int
}

class FootballServiceImpl : FootballService {
    
    override suspend fun fetchJugadoresByEquipo(equipoId: Int): List<Jugador> = emptyList()
    override suspend fun fetchJugadoresConMasGoles(minGoles: Int): List<Jugador> = emptyList()
    

    override suspend fun fetchResultadosPartidos(): List<ResulPartido> = emptyList()

    override suspend fun fetchTotalGolesEquipo(equipoId: Int): Int = 0
}
