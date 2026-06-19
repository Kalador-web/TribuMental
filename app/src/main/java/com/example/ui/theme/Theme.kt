package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = PrimaryDeepPurple,
    onPrimary = Color.White,
    primaryContainer = SoftLila,
    onPrimaryContainer = PrimaryDeepPurple,
    secondary = SecondaryDustPink,
    onSecondary = Color.White,
    secondaryContainer = PowderPink,
    onSecondaryContainer = DeepCharcoalText,
    tertiary = TerciaryWarmTeal,
    onTertiary = Color.White,
    background = CreamBackground,
    onBackground = DeepCharcoalText,
    surface = WarmCardWhite,
    onSurface = DeepCharcoalText,
    surfaceVariant = SoftBorderPlum,
    onSurfaceVariant = MutedSlateSub,
    outline = SoftBorderPlum,
    error = AlertSoftRed,
    onError = DeepCharcoalText
)

// Warm organic dark colors if user has dark mode enabled
private val DarkColorScheme = darkColorScheme(
    primary = TerciaryWarmTeal,
    onPrimary = Color.White,
    primaryContainer = PrimaryDeepPurple,
    onPrimaryContainer = Color.White,
    secondary = SecondaryDustPink,
    onSecondary = Color.White,
    background = Color(0xFF231E1B),   // Dark earth tone brown/gray
    onBackground = Color(0xFFF2EDE7), // Soft warm paper tone
    surface = Color(0xFF2E2723),      // Dark warm cardboard/wood tone
    onSurface = Color(0xFFF2EDE7),
    surfaceVariant = Color(0xFF3D3530),
    onSurfaceVariant = Color(0xFFCDC4B9),
    outline = Color(0xFF4C423C)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = ThemeConfig.isDarkMode,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
