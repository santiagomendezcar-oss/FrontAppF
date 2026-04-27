package com.example.myapplication.service

import com.example.myapplication.model.Equipo
import com.example.myapplication.model.Jugador
import com.example.myapplication.model.ResulPartido
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface ApiService {
    @GET("api/equipos")
    suspend fun getEquipos(): List<Equipo>

    @GET("api/equipos/{equipoId}")
    suspend fun getEquipoById(@Path("equipoId") equipoId: Int): Equipo

    @GET("api/jugadores")
    suspend fun getJugadores(): List<Jugador>

    @GET("api/jugadores/equipo/{equipoId}")
    suspend fun getJugadoresByEquipo(@Path("equipoId") equipoId: Int): List<Jugador>

    @GET("api/partidos")
    suspend fun getPartidos(): List<ResulPartido>

    @GET("api/estadisticas/top-goleadores")
    suspend fun getTopGoleadores(): List<Map<String, Any>>

    @GET("api/partidos/resultados")
    suspend fun getResultadosPartidos(): List<ResulPartido>

    @GET("api/partidos/total-goles/{equipoId}")
    suspend fun getTotalGolesEquipo(@Path("equipoId") equipoId: Int): Map<String, Int>

    companion object {
        private const val BASE_URL = "https://appf-yda9.onrender.com/"

        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
