package com.example.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SofaColorScheme = lightColorScheme(
    primary = SofaBlue,
    secondary = SofaAccent,
    tertiary = SofaDarkBlue,
    background = SofaBackground,
    surface = SofaSurface,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = SofaTextPrimary,
    onSurface = SofaTextPrimary
)

private val DarkSofaColorScheme = darkColorScheme(
    primary = SofaBlue,
    secondary = SofaAccent,
    tertiary = SofaLightBlue,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkSofaColorScheme else SofaColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
