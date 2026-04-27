package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JugadoresGolesScreen(navController: NavHostController, viewModel: MainViewModel) {
    var minGoles by remember { mutableStateOf("0") }
    val jugadores by viewModel.jugadores.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.loadJugadoresConMasGoles(minGoles.toIntOrNull() ?: 0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MÁXIMOS GOLEADORES", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(SofaBackground)
        ) {
            // Filtro SofaScore Style
            Surface(
                color = SofaDarkBlue,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.AutoMirrored.Filled.List, contentDescription = null, tint = SofaAccent)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Filtrar por más de:",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedTextField(
                        value = minGoles,
                        onValueChange = { 
                            minGoles = it
                            viewModel.loadJugadoresConMasGoles(it.toIntOrNull() ?: 0)
                        },
                        modifier = Modifier.width(80.dp).height(50.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = SofaAccent,
                            unfocusedBorderColor = Color.White.copy(alpha = 0.5f)
                        ),
                        singleLine = true
                    )
                    Text(" goles", color = Color.White, modifier = Modifier.padding(start = 8.dp))
                }
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = SofaBlue)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        // Table Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("#", modifier = Modifier.width(30.dp), color = SofaTextSecondary, fontWeight = FontWeight.Bold)
                            Text("JUGADOR", modifier = Modifier.weight(1f), color = SofaTextSecondary, fontWeight = FontWeight.Bold)
                            Text("GOLES", color = SofaTextSecondary, fontWeight = FontWeight.Bold)
                        }
                    }

                    itemsIndexed(jugadores) { index, jugador ->
                        val maxGoles = jugadores.firstOrNull()?.goles ?: 1
                        ScorerItem(
                            rank = index + 1,
                            nombre = jugador.nombre ?: "Desconocido",
                            equipo = jugador.nombreEquipo ?: "",
                            goles = jugador.goles ?: 0,
                            maxGoles = maxGoles
                        )
                        HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }
}

@Composable
fun ScorerItem(rank: Int, nombre: String, equipo: String, goles: Int, maxGoles: Int) {
    val progress = if (maxGoles > 0) goles.toFloat() / maxGoles else 0f
    
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rank.toString(),
                    modifier = Modifier.width(30.dp),
                    fontWeight = FontWeight.Bold,
                    color = when (rank) {
                        1 -> Color(0xFFFFD700) // Oro
                        2 -> Color(0xFFC0C0C0) // Plata
                        3 -> Color(0xFFCD7F32) // Bronce
                        else -> SofaTextPrimary
                    }
                )
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = nombre, fontWeight = FontWeight.SemiBold, color = SofaTextPrimary)
                    Text(text = equipo, style = MaterialTheme.typography.bodySmall, color = SofaTextSecondary)
                }
                
                Surface(
                    color = SofaLightBlue,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = goles.toString(),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontWeight = FontWeight.ExtraBold,
                        color = SofaBlue
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Barra de rendimiento
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth().height(4.dp),
                color = if (rank <= 3) SofaAccent else SofaBlue,
                trackColor = Color.LightGray.copy(alpha = 0.3f),
                strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
            )
        }
    }
}
