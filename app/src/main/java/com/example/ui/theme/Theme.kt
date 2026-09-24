package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val BartrColorScheme = lightColorScheme(
    primary = BartrBlue,
    onPrimary = Color.White,
    primaryContainer = BartrBlueTint,
    onPrimaryContainer = BartrBlueDark,
    secondary = BartrBlueDark,
    onSecondary = Color.White,
    secondaryContainer = BartrBackdrop,
    onSecondaryContainer = BartrInk,
    tertiary = BartrSuccess,
    onTertiary = Color.White,
    tertiaryContainer = BartrGreenTint,
    onTertiaryContainer = BartrInk,
    background = Color.White,
    onBackground = BartrInk,
    surface = Color.White,
    onSurface = BartrInk,
    surfaceVariant = BartrBackdrop,
    onSurfaceVariant = BartrInkSoft,
    error = BartrDanger,
    onError = Color.White,
    errorContainer = BartrRedTint,
    onErrorContainer = BartrDanger
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = BartrColorScheme,
        typography = Typography,
        content = content
    )
}

