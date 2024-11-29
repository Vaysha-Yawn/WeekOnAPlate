package week.on.a.plate.screens.settings.event

import android.content.Context
import week.on.a.plate.core.Event

sealed class SettingsEvent : Event() {
    data class Theme (val context: Context):SettingsEvent()
    data class Tutorial (val context: Context):SettingsEvent()
    data class SetStdPortionsCount (val context: Context):SettingsEvent()
    data class SetMenuSelections (val context: Context):SettingsEvent()
    data class Import (val context: Context):SettingsEvent()
    data class Export (val context: Context):SettingsEvent()
    data class BigType (val context: Context):SettingsEvent()
    data class Profile (val context: Context):SettingsEvent()
    data class RateApp (val context: Context):SettingsEvent()
    data class Premium (val context: Context):SettingsEvent()
    data class PrivacyPolicy (val context: Context):SettingsEvent()
}