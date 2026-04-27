package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultadosScreen(navController: NavHostController, viewModel: MainViewModel) {
    val resultados by viewModel.resultados.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    
    var visibleCount by remember { mutableIntStateOf(10) }

    LaunchedEffect(Unit) {
        viewModel.loadResultadosPartidos()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("MARCADORES", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF0F4F0))) {
            if (isLoading && resultados.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                    item { Spacer(modifier = Modifier.height(8.dp)) }

                    val displayedPartidos = resultados.take(visibleCount)
                    
                    items(displayedPartidos) { partido ->
                        // Formateamos la fecha para que solo muestre YYYY-MM-DD
                        val fechaCorta = if (partido.fecha_partido.length >= 10) {
                            partido.fecha_partido.substring(0, 10)
                        } else {
                            partido.fecha_partido
                        }

                        MatchCard(
                            local = partido.nombreEquipoLocal,
                            visita = partido.nombreEquipoVisita,
                            gLocal = partido.goles_local,
                            gVisita = partido.goles_visita,
                            fecha = fechaCorta,
                            estadio = partido.estadio
                        )
                    }

                    if (resultados.size > visibleCount) {
                        item {
                            Button(
                                onClick = { visibleCount += 10 },
                                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("CARGAR MÁS", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MatchCard(local: String, visita: String, gLocal: Int, gVisita: Int, fecha: String, estadio: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Local
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = local, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                
                // Marcador
                Surface(
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(horizontal = 12.dp)
                ) {
                    Text(
                        text = "$gLocal - $gVisita",
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black
                    )
                }

                // Visita
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = visita, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$fecha | $estadio",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}
