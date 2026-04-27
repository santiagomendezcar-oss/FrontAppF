package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Sports
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleEquipoScreen(navController: NavHostController, viewModel: MainViewModel, equipoId: Int) {
    val jugadores by viewModel.jugadores.observeAsState(emptyList())
    val equipo by viewModel.equipoSeleccionado.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)

    fun refreshData() {
        viewModel.loadEquipoById(equipoId)
        viewModel.loadJugadoresByEquipo(equipoId)
    }

    LaunchedEffect(equipoId) {
        refreshData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(equipo?.nombre?.uppercase() ?: "EQUIPO", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SofaBlue,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = isLoading,
            onRefresh = { refreshData() },
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                // Cabecera con Info Real del Equipo
                Card(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = SofaDarkBlue),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        // Obtener datos locales si la API no los trae
                        val estadioLocal = when(equipoId) {
                            1 -> "Santiago Bernabéu"
                            2 -> "Spotify Camp Nou"
                            3 -> "Etihad Stadium"
                            4 -> "Allianz Arena"
                            5 -> "Parc des Princes"
                            else -> equipo?.estadio ?: "Desconocido"
                        }
                        
                        val entrenadorLocal = when(equipoId) {
                            1 -> "Carlo Ancelotti"
                            2 -> "Hansi Flick"
                            3 -> "Pep Guardiola"
                            4 -> "Vincent Kompany"
                            5 -> "Luis Enrique"
                            else -> equipo?.entrenador ?: "Desconocido"
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Ciudad
                            InfoColumn(
                                icon = Icons.Default.Sports,
                                label = "CIUDAD",
                                value = if (equipo?.ciudad.isNullOrEmpty()) "Cargando..." else equipo?.ciudad!!
                            )
                            // Estadio (Local)
                            InfoColumn(
                                icon = Icons.Default.LocationOn,
                                label = "ESTADIO",
                                value = estadioLocal
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Fundación
                            InfoColumn(
                                icon = Icons.Default.DateRange,
                                label = "FUNDACIÓN",
                                value = if (equipo?.fundacion.isNullOrEmpty()) "Cargando..." else equipo?.fundacion!!
                            )
                            // Entrenador (Local)
                            InfoColumn(
                                icon = Icons.Default.Person,
                                label = "ENTRENADOR",
                                value = entrenadorLocal
                            )
                        }
                    }
                }

                Text(
                    "PLANTILLA ACTUAL",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = SofaTextSecondary,
                    fontWeight = FontWeight.Bold
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(jugadores) { jugador ->
                        PlayerItem(
                            nombre = jugador.nombre ?: "Sin nombre",
                            posicion = jugador.posicion ?: "N/A",
                            dorsal = jugador.dorsal?.toString() ?: "0"
                        )
                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            }
        }
    }
}

@Composable
fun InfoColumn(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.width(150.dp)
    ) {
        Icon(icon, contentDescription = null, tint = SofaAccent, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(label, color = Color.White.copy(alpha = 0.6f), fontSize = 9.sp, fontWeight = FontWeight.Bold)
            Text(value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun PlayerItem(nombre: String, posicion: String, dorsal: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(32.dp),
                shape = RoundedCornerShape(4.dp),
                color = SofaBlue
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(dorsal, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(nombre, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(posicion, color = SofaTextSecondary, fontSize = 12.sp)
            }
        }
    }
}
