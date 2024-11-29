package week.on.a.plate.screens.settings.logic

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.preference.PreferenceUseCase
import week.on.a.plate.screens.settings.event.SettingsEvent
import week.on.a.plate.screens.settings.state.SettingsUIState
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    val state = SettingsUIState()
    private val pref = PreferenceUseCase()

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
            is SettingsEvent.BigType -> bigType(event.context)
            is SettingsEvent.Export -> export(event.context)
            is SettingsEvent.Import -> import(event.context)
            is SettingsEvent.Premium -> premium(event.context)
            is SettingsEvent.PrivacyPolicy -> privacyPolicy(event.context)
            is SettingsEvent.Profile -> profile(event.context)
            is SettingsEvent.RateApp -> rateApp(event.context)
            is SettingsEvent.SetMenuSelections -> setMenuSelections(event.context)
            is SettingsEvent.SetStdPortionsCount -> setStdPortionsCount(event.context)
            is SettingsEvent.Theme -> theme(event.context)
            is SettingsEvent.Tutorial -> tutorial(event.context)
        }
    }

    private fun bigType(context: Context) {

    }

    private fun export(context: Context) {

    }

    private fun import(context: Context) {

    }

    private fun premium(context: Context) {

    }

    private fun privacyPolicy(context: Context) {

    }

    private fun profile(context: Context) {

    }

    private fun rateApp(context: Context) {

    }

    private fun setMenuSelections(context: Context) {

    }

    private fun setStdPortionsCount(context: Context) {
        viewModelScope.launch {
            val current = pref.getDefaultPortionsCount(context)
            val vm = ChangePortionsCountViewModel()
            vm.mainViewModel = mainViewModel
            onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(current) { count ->
                pref.saveDefaultPortionsCount(context, count)
            }
        }
    }

    private fun theme(context: Context) {

    }

    private fun tutorial(context: Context) {

    }

}