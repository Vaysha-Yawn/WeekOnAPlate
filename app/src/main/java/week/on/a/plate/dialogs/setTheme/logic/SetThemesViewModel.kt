package week.on.a.plate.dialogs.setTheme.logic


import android.content.Context
import android.content.Intent
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.setTheme.event.SetThemeEvent
import week.on.a.plate.dialogs.setTheme.state.SetThemeUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.mainActivity.view.MainActivity
import week.on.a.plate.preference.PreferenceUseCase


class SetThemesViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = SetThemeUIState()
    private val prefs = PreferenceUseCase()

    fun onEvent(event: SetThemeEvent) {
        when (event) {
            SetThemeEvent.Close -> close()
            is SetThemeEvent.Select -> select(event.themeId, event.context)
        }
    }

    fun launch(context: Context){
        state.selectedInd.value = prefs.getActiveThemeId(context)
    }

    fun select(themeId: Int, context: Context) {
        mainViewModel.viewModelScope.launch {
            prefs.saveActiveThemeId(context, themeId)
            prefs.restartActivity(context)
        }
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }
}