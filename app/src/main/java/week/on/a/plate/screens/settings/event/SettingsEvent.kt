package week.on.a.plate.screens.settings.event

import week.on.a.plate.core.Event

sealed class SettingsEvent : Event() {
    data object Done:SettingsEvent()
}