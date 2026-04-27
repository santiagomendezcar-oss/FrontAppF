package com.example.myapplication.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.filled.EmojiEvents

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, isDarkMode: Boolean, onThemeToggle: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        "SCORENOW",
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        fontSize = 24.sp,
                        letterSpacing = 1.2.sp
                    )
                },
                actions = {
                    IconButton(onClick = onThemeToggle) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Cambiar tema",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SofaBlue
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Sección de equipos destacados arriba
            Surface(
                color = SofaDarkBlue,
                shadowElevation = 4.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(vertical = 12.dp)) {
                    Text(
                        "EQUIPOS DESTACADOS",
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                    val equipos = listOf(
                        "1" to ("RM" to "Real Madrid"),
                        "2" to ("BAR" to "Barcelona"),
                        "3" to ("MC" to "Man. City"),
                        "4" to ("BAY" to "Bayern"),
                        "5" to ("PSG" to "PSG")
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        items(equipos) { (id, data) ->
                            val (siglas, nombre) = data
                            TeamItem(siglas, nombre) {
                                navController.navigate("detalle_equipo/$id")
                            }
                        }
                    }
                }
            }

            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(Brush.horizontalGradient(listOf(SofaBlue, SofaDarkBlue)))
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("TEMPORADA 2026", color = SofaAccent, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
                            Text("Estadísticas en Vivo", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text("Analiza el rendimiento de tus equipos favoritos", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                        }
                        Icon(
                            Icons.Default.Star, 
                            contentDescription = null, 
                            tint = SofaAccent, 
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }

            // Título de la seccion de abajo
            Text(
                "PANEL DE CONTROL",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.labelMedium,
                color = SofaTextSecondary,
                fontWeight = FontWeight.Bold
            )

            
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SofaMenuTile("Plantillas de Jugadores", Icons.Default.Person, "jugadores_equipo", navController, isDarkMode)
                SofaMenuTile("Ranking de Goleadores", Icons.Default.EmojiEvents, "jugadores_goles", navController, isDarkMode)
                SofaMenuTile("Historial de Resultados", Icons.AutoMirrored.Filled.List, "resultados", navController, isDarkMode)
                SofaMenuTile("Goles por Equipo", Icons.Default.Info, "total_goles", navController, isDarkMode)
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            
            Card(
                modifier = Modifier.padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(0.5.dp, Color.LightGray)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Info, 
                        contentDescription = null, 
                        tint = SofaAccent, 
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "¿Sabías que puedes filtrar goleadores por cantidad mínima de goles?",
                        style = MaterialTheme.typography.bodySmall,
                        color = SofaTextPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun TeamItem(siglas: String, nombre: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Surface(
            modifier = Modifier.size(50.dp),
            shape = CircleShape,
            color = Color.White.copy(alpha = 0.1f),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(siglas, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(nombre, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun SofaMenuTile(title: String, icon: ImageVector, route: String, navController: NavHostController, isDarkMode: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate(route) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(36.dp),
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        icon, 
                        contentDescription = null, 
                        tint = if (isDarkMode) Color.White else MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f),
                fontSize = 15.sp
            )
            Icon(
                Icons.AutoMirrored.Filled.KeyboardArrowRight, 
                contentDescription = null, 
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        }
    }
}
