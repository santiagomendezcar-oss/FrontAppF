package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JugadoresEquipoScreen(navController: NavHostController, viewModel: MainViewModel) {
    var equipoId by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val equiposList = listOf(
        "1" to "Real Madrid",
        "2" to "FC Barcelona",
        "3" to "Manchester City",
        "4" to "Bayern Munich",
        "5" to "Paris Saint-Germain"
    )
    var selectedEquipoName by remember { mutableStateOf("Selecciona un equipo") }

    val jugadores by viewModel.jugadores.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)


    LaunchedEffect(Unit) {
        if (equipoId.isEmpty()) {
            viewModel.clearJugadores()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("PLANTILLAS", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF8F9FA))
                .padding(16.dp)
        ) {

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            ) {
                OutlinedTextField(
                    value = selectedEquipoName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Equipo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    equiposList.forEach { (id, nombre) ->
                        DropdownMenuItem(
                            text = { Text(nombre) },
                            onClick = {
                                selectedEquipoName = nombre
                                equipoId = id
                                expanded = false
                                viewModel.loadJugadoresByEquipo(id.toInt())
                            }
                        )
                    }
                }
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (jugadores.isEmpty() && equipoId.isNotEmpty()) {
                // Empty State (Sugerencia 3)
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🔍", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("No se encontraron jugadores", color = Color.Gray)
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(jugadores) { jugador ->
                        PlayerCard(
                            nombre = jugador.nombre ?: "Desconocido",
                            posicion = jugador.posicion ?: "N/A",
                            dorsal = jugador.dorsal?.toString() ?: "0"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlayerCard(nombre: String, posicion: String, dorsal: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // jugador
            Surface(
                modifier = Modifier.size(50.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(text = nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = posicion, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            
            // dorsal de la camiseta
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = dorsal, color = Color.White, fontWeight = FontWeight.Black)
            }
        }
    }
}
