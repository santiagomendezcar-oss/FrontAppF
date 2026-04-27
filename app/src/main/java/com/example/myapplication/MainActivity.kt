package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.MainViewModel
import com.example.myapplication.screen.HomeScreen
import com.example.myapplication.screen.JugadoresEquipoScreen
import com.example.myapplication.screen.JugadoresGolesScreen
import com.example.myapplication.screen.ResultadosScreen
import com.example.myapplication.screen.TotalGolesScreen
import com.example.myapplication.screen.DetalleEquipoScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkMode by remember { mutableStateOf(false) }
            MyApplicationTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FootballApp(isDarkMode = isDarkMode, onThemeToggle = { isDarkMode = !isDarkMode })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FootballApp(
    mainViewModel: MainViewModel = viewModel(),
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { 
            HomeScreen(
                navController = navController, 
                isDarkMode = isDarkMode, 
                onThemeToggle = onThemeToggle
            ) 
        }
        composable("jugadores_equipo") { JugadoresEquipoScreen(navController, mainViewModel) }
        composable("jugadores_goles") { JugadoresGolesScreen(navController, mainViewModel) }
        composable("resultados") { ResultadosScreen(navController, mainViewModel) }
        composable("total_goles") { TotalGolesScreen(navController, mainViewModel) }
        composable("detalle_equipo/{equipoId}") { backStackEntry ->
            val equipoId = backStackEntry.arguments?.getString("equipoId")?.toIntOrNull() ?: 1
            DetalleEquipoScreen(navController, mainViewModel, equipoId)
        }
    }
}
