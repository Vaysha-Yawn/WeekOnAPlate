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
    tertiary = ColorBlueButton,
    background = ColorTextBlack,
    surface = ColorTextBlack,
    onPrimary = ColorTextBlack,
    onSecondary = ColorTextBlack,
    onTertiary = ColorTextBlack,
    onBackground = ColorBackgroundWhite,
    onSurface = ColorBackgroundWhite,
)

private val LightColorScheme = lightColorScheme(
    primary = ColorPanelYellow,
    secondary = ColorPanelGreen,
    tertiary = ColorBluePanel,
    background = ColorBackgroundWhite,
    surface = ColorBackgroundWhite,
    onPrimary = ColorTextBlack,
    onSecondary = ColorTextBlack,
    onTertiary = ColorTextBlack,
    onBackground = ColorTextBlack,
    onSurface = ColorTextBlack,
)

@Composable
fun WeekOnAPlateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}