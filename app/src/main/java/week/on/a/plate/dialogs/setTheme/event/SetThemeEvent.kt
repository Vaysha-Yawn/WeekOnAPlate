package week.on.a.plate.dialogs.setTheme.event

import androidx.activity.ComponentActivity
import week.on.a.plate.core.Event

sealed class SetThemeEvent: Event() {
    data class Select(val themeId: Int, val context: ComponentActivity): SetThemeEvent()
    data object Close: SetThemeEvent()
}