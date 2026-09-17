package com.example.borowallet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = BoroPrimary,
    onPrimary = Color.White,
    primaryContainer = BoroAccentSoft,
    onPrimaryContainer = BoroPrimaryVariant,
    secondary = BoroPrimaryVariant,
    onSecondary = Color.White,
    background = LightBackground,
    onBackground = DarkText,
    surface = LightSurface,
    onSurface = DarkText,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = SecondaryText,
    outline = BorderSubtle
)

private val DarkColorScheme = darkColorScheme(
    primary = BoroPrimaryVariant,
    onPrimary = Color.White,
    primaryContainer = Color(0xFF2A1520),
    onPrimaryContainer = Color(0xFFFFB2C9),
    secondary = BoroAccent,
    onSecondary = Color.Black,
    background = Color(0xFF121417),
    onBackground = Color(0xFFF1F2F4),
    surface = Color(0xFF1A1C20),
    onSurface = Color(0xFFF1F2F4),
    surfaceVariant = Color(0xFF22262C),
    onSurfaceVariant = Color(0xFFC0C5CF),
    outline = Color(0xFF2E333B)
)

@Composable
fun BoroWalletTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
