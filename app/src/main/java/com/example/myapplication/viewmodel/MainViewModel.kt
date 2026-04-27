package com.example.myapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.Equipo
import com.example.myapplication.model.Jugador
import com.example.myapplication.model.ResulPartido
import com.example.myapplication.repository.FootballRepository
import kotlinx.coroutines.launch
import android.util.Log

class MainViewModel : ViewModel() {

    private val repository = FootballRepository()

    private val _equipoSeleccionado = MutableLiveData<Equipo?>(null)
    val equipoSeleccionado: LiveData<Equipo?> = _equipoSeleccionado

    private val _equipos = MutableLiveData<List<Equipo>>(emptyList())
    val equipos: LiveData<List<Equipo>> = _equipos

    private val _jugadores = MutableLiveData<List<Jugador>>(emptyList())
    val jugadores: LiveData<List<Jugador>> = _jugadores

    private val _resultados = MutableLiveData<List<ResulPartido>>(emptyList())
    val resultados: LiveData<List<ResulPartido>> = _resultados

    private val _totalGoles = MutableLiveData<Int>(0)
    val totalGoles: LiveData<Int> = _totalGoles

    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadResultadosPartidos() {
        viewModelScope.launch {
            if (_isLoading.value == true) return@launch
            
            _isLoading.value = true
            try {
                Log.d("MainViewModel", "Iniciando carga de partidos...")
                val result = repository.getResultadosPartidos()
                _resultados.postValue(result)
                Log.d("MainViewModel", "Carga finalizada: ${result.size} partidos")
            } catch (e: Exception) {
                Log.e("MainViewModel", "ERROR AL CARGAR: ${e.message}")
                _resultados.postValue(emptyList())
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadJugadoresByEquipo(equipoId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getJugadoresByEquipo(equipoId)
                _jugadores.postValue(result)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearJugadores() {
        _jugadores.value = emptyList()
    }

    fun loadEquipos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getEquipos()
                _equipos.postValue(result)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadEquipoById(equipoId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getEquipoById(equipoId)
                _equipoSeleccionado.postValue(result)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadJugadoresConMasGoles(minGoles: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getJugadoresConMasGoles(minGoles)
                _jugadores.postValue(result)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error cargando goleadores: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadTotalGolesEquipo(equipoId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getTotalGolesEquipo(equipoId)
                _totalGoles.postValue(result)
            } catch (e: Exception) {
                Log.e("MainViewModel", "Error cargando total goles: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }
}
