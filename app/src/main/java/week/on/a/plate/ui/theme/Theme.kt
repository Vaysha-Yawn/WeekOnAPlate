package week.on.a.plate.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = ColorButtonYellow,
    secondary = ColorButtonGreen,
    tertiary = ColorSurfaceBlack,
    background = ColorTextBlack,
    surface = ColorSurfaceBlack,
    onPrimary = ColorTextBlack,
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
    tertiary = ColorPanelLightGrey,
    background = ColorPanelLightGrey,
    surface = ColorBackgroundWhite,
    onPrimary = ColorTextBlack,
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
   /* val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }*/

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}