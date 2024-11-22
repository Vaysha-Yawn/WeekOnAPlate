package week.on.a.plate.screens.settings.logic

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.preference.PreferenceUseCase
import week.on.a.plate.screens.settings.event.SettingsEvent
import week.on.a.plate.screens.settings.state.SettingsUIState
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    lateinit var state: SettingsUIState
    val pref = PreferenceUseCase

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> {
                mainViewModel.onEvent(event)
            }

            is SettingsEvent -> {
                onEvent(event)
            }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {

            else -> {}
        }
    }

}