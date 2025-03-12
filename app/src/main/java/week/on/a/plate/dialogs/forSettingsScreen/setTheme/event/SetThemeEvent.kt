package week.on.a.plate.dialogs.forSettingsScreen.setTheme.event

import androidx.activity.ComponentActivity
import week.on.a.plate.core.Event

sealed interface SetThemeEvent : Event {
    class Select(val themeId: Int, val context: ComponentActivity) : SetThemeEvent
    object Close : SetThemeEvent
}