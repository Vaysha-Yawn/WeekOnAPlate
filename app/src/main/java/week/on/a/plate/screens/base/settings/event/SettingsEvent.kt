package week.on.a.plate.screens.base.settings.event

import android.content.Context
import week.on.a.plate.core.Event

sealed interface SettingsEvent : Event {
    object TermsOfUse : SettingsEvent
    class Theme(val context: Context) : SettingsEvent
    class Tutorial(val context: Context) : SettingsEvent
    class SetStdPortionsCount(val context: Context) : SettingsEvent
    class SetMenuSelections(val context: Context) : SettingsEvent
    class Import(val context: Context) : SettingsEvent
    class Export(val context: Context) : SettingsEvent
    class BigType(val context: Context) : SettingsEvent
    class Profile(val context: Context) : SettingsEvent
    class RateApp(val context: Context) : SettingsEvent
    class Premium(val context: Context) : SettingsEvent
    class PrivacyPolicy(val context: Context) : SettingsEvent
}