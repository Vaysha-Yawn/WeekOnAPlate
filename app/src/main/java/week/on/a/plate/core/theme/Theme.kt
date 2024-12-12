package week.on.a.plate.core.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import week.on.a.plate.preference.PreferenceUseCase


val DarkColorScheme = darkColorScheme(
    primary = ColorButtonYellowDark,
    secondary = ColorDarkButtonGreen,
    secondaryContainer = ColorDarkButtonOutlineGreen,
    tertiary = ColorSurfaceBlack,
    background = ColorTextBlack,
    surface = ColorSurfaceBlack,
    onPrimary = ColorButtonYellow,
    onSecondary = ColorTextBlack,
    onTertiary = ColorTextBlack,
    onBackground = ColorPanelLightGrey,
    onSurface = ColorStrokeGrey,
    outline = ColorSubTextGrey,
    surfaceVariant = ColorTextBlack,
)

val LightColorScheme = lightColorScheme(
    primary = ColorButtonYellow,
    secondary = ColorButtonGreen,
    secondaryContainer = ColorButtonGreenStroke,
    tertiary = ColorPanelLightGrey,
    background = ColorPanelLightGrey,
    surface = ColorBackgroundWhite,
    onPrimary = ColorBorder,
    onSecondary = ColorTextBlack,
    onTertiary = ColorTextBlack,
    onBackground = ColorTextBlack,
    onSurface = ColorButtonNegativeGrey,
    outline = ColorButtonNegativeGrey,
    surfaceVariant = ColorPanelLightGrey,
)

@Composable
fun WeekOnAPlateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val themeInd = PreferenceUseCase().getActiveThemeId(LocalContext.current)
    val group = palettes[themeInd]
    val colorScheme = group.getColorScheme(darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}