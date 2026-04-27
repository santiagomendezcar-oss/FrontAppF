package com.example.myapplication.repository

import com.example.myapplication.model.Equipo
import com.example.myapplication.model.Jugador
import com.example.myapplication.model.ResulPartido
import com.example.myapplication.service.ApiService
import com.example.myapplication.service.FootballService
import com.example.myapplication.service.FootballServiceImpl
import android.util.Log

class FootballRepository(
    private val apiService: ApiService = ApiService.create(),
    private val localService: FootballService = FootballServiceImpl()
) {

    suspend fun getJugadoresByEquipo(equipoId: Int): List<Jugador> {
        return try {
            apiService.getJugadoresByEquipo(equipoId)
        } catch (e: Exception) {
            Log.e("FootballRepository", "Error API jugadores: ${e.message}")
            emptyList()
        }
    }

    suspend fun getEquipos(): List<Equipo> {
        return try {
            apiService.getEquipos()
        } catch (e: Exception) {
            Log.e("FootballRepository", "Error API equipos: ${e.message}")
            emptyList()
        }
    }

    suspend fun getEquipoById(equipoId: Int): Equipo? {
        return try {
            apiService.getEquipoById(equipoId)
        } catch (e: Exception) {
            Log.e("FootballRepository", "Error API equipo id: ${e.message}")
            null
        }
    }

    suspend fun getJugadoresConMasGoles(minGoles: Int): List<Jugador> {
        return try {
            val response = apiService.getTopGoleadores()
            response.mapNotNull { item ->
                val golesVal = item["total_goles"] ?: item["goles"]
                val goles = (golesVal as? Number)?.toInt() ?: 0
                if (goles >= minGoles) {
                    Jugador(
                        id_jugador = (item["id_jugador"] as? Number)?.toInt() ?: 0,
                        nombre = item["nombre"] as? String ?: "Desconocido",
                        posicion = item["posicion"] as? String ?: "",
                        dorsal = (item["dorsal"] as? Number)?.toInt() ?: 0,
                        nombreEquipo = item["nombre_equipo"] as? String ?: "",
                        goles = goles
                    )
                } else null
            }
        } catch (e: Exception) {
            Log.e("FootballRepository", "Error API goleadores: ${e.message}")
            emptyList()
        }
    }

    suspend fun getResultadosPartidos(): List<ResulPartido> {
        return try {
            Log.d("FootballRepository", "Consultando API en Render...")
            val result = apiService.getPartidos() 
            Log.d("FootballRepository", "Respuesta recibida: ${result.size} partidos")
            result
        } catch (e: Exception) {
            Log.e("FootballRepository", "ERROR CRÍTICO AL CONECTAR AL BACKEND: ${e.message}")
            // Si falla la API, ahora devolvemos lista vacía (sin datos viejos)
            emptyList()
        }
    }

    suspend fun getTotalGolesEquipo(equipoId: Int): Int {
        return try {
            val response = apiService.getTotalGolesEquipo(equipoId)
            response["totalGoles"] ?: response["total_goles"] ?: 0
        } catch (e: Exception) {
            Log.e("FootballRepository", "Error API total goles: ${e.message}")
            0
        }
    }
}
