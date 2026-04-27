package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TotalGolesScreen(navController: NavHostController, viewModel: MainViewModel) {
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

    val totalGoles by viewModel.totalGoles.observeAsState(0)
    val isLoading by viewModel.isLoading.observeAsState(false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GOLES POR EQUIPO", fontWeight = FontWeight.Bold, fontSize = 18.sp) },
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
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                OutlinedTextField(
                    value = selectedEquipoName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Equipo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
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
                                viewModel.loadTotalGolesEquipo(id.toInt())
                            }
                        )
                    }
                }
            }

            if (isLoading) {
                CircularProgressIndicator(color = SofaBlue)
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "TOTAL DE GOLES",
                            style = MaterialTheme.typography.labelLarge,
                            color = SofaTextSecondary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "$totalGoles",
                            style = MaterialTheme.typography.displayLarge,
                            color = SofaBlue,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = selectedEquipoName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = SofaTextPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}
