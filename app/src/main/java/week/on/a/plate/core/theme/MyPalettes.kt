package week.on.a.plate.core.theme

import androidx.compose.ui.graphics.Color
import week.on.a.plate.R

val palettes = listOf(
    ColorPaletteGroup(
        R.string.classic,
        ColorPalette(
            primary = LightColorScheme.primary,
            secondary = LightColorScheme.secondary,
            background = LightColorScheme.background,
            surface = LightColorScheme.surface,
        ),
        ColorPalette(
            primary = DarkColorScheme.primary,
            secondary = DarkColorScheme.secondary,
            background = DarkColorScheme.background,
            surface = DarkColorScheme.surface,
        )
    ),
    ColorPaletteGroup(
        R.string.Evening,
        ColorPalette(
            primary = Color(0xFFFFB6D1),
            secondary = Color(0xFFB9B4FF),
            background = Color(0xFFD8CAFC),
            surface = Color(0xFFE9E1FF),
        ),
        ColorPalette(
            primary = Color(0xFFCB348F),
            secondary = Color(0xFF7334CB),
            background = Color(0xFF2A2044),
            surface = Color(0xFF3D335B),
        )
    )
)