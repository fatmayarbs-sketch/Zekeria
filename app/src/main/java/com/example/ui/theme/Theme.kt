package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = Color(0xFF62DC8C),
  onPrimary = Color(0xFF00391B),
  primaryContainer = MauritaniaGreen,
  onPrimaryContainer = Color(0xFF8FFFA8),
  secondary = MauritaniaGold,
  tertiary = Color(0xFFFFB3B4),
  background = Color(0xFF101411),
  surface = Color(0xFF181C19),
  onBackground = Color(0xFFE1E3DF),
  onSurface = Color(0xFFE1E3DF)
)

private val LightColorScheme = lightColorScheme(
  primary = MauritaniaGreen,
  onPrimary = Color.White,
  primaryContainer = MauritaniaGreenLight,
  onPrimaryContainer = MauritaniaGreenDark,
  secondary = MauritaniaGold,
  onSecondary = Color.White,
  secondaryContainer = MauritaniaGoldLight,
  tertiary = MauritaniaRed,
  onTertiary = Color.White,
  tertiaryContainer = MauritaniaRedLight,
  background = BackgroundLight,
  onBackground = TextPrimary,
  surface = SurfaceLight,
  onSurface = TextPrimary,
  outline = OutlineBorder
)

@Composable
fun MauritaniaSchoolsTheme(
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

