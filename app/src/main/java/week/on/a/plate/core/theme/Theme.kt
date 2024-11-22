package week.on.a.plate.core.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
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

val PurpleRedDarkColorScheme = ColorPalette(
    primary = Color(0xFFCB3436),
    secondary = Color(0xFF463DB2),
)

val PurpleRedLightColorScheme = ColorPalette(
    primary = Color(0xFFFF9C9C),
    secondary = Color(0xFFB9B4FF),
)

@Composable
fun WeekOnAPlateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    //todo customtheme
    val colorScheme = if (darkTheme) DarkColorScheme.copy(primary = PurpleRedDarkColorScheme.primary, secondary = PurpleRedDarkColorScheme.secondary)
    else LightColorScheme.copy(primary = PurpleRedLightColorScheme.primary, secondary = PurpleRedLightColorScheme.secondary)


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}