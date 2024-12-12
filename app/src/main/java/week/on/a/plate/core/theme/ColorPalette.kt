package week.on.a.plate.core.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color



class ColorPaletteGroup (
    val name:String,
    val light:ColorPalette,
    val dark:ColorPalette,
){
    fun getColorScheme(isDarkTheme:Boolean):ColorScheme{
        return if (isDarkTheme) dark.getColorScheme(true)
        else light.getColorScheme(false)
    }
}

class ColorPalette (
    val primary:Color,
    val secondary:Color,
    val surface:Color,
    val background:Color,
){
    fun getColorScheme(isDarkTheme:Boolean): ColorScheme {
        return if (isDarkTheme) DarkColorScheme.copy(primary = primary, secondary = secondary, surface = surface, background = background)
        else LightColorScheme.copy(primary = primary, secondary = secondary, surface = surface, background = background)
    }
}
