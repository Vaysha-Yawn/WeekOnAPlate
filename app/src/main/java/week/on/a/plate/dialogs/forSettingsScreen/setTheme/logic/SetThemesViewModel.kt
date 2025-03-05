package week.on.a.plate.dialogs.forSettingsScreen.setTheme.logic


import android.content.Context
import android.content.Intent
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.forSettingsScreen.setTheme.event.SetThemeEvent
import week.on.a.plate.dialogs.forSettingsScreen.setTheme.state.SetThemeUIState
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.app.mainActivity.view.MainActivity
import week.on.a.plate.data.preference.PreferenceUseCase


class SetThemesViewModel(
    context: Context,
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
) : DialogViewModel<Boolean>(
    scope,
    openDialog,
    closeDialog,
    {}
) {
    val state = SetThemeUIState()
    private val prefs = PreferenceUseCase()

    init{
        state.selectedInd.value = prefs.getActiveThemeId(context)
    }

    fun onEvent(event: SetThemeEvent) {
        when (event) {
            SetThemeEvent.Close -> close()
            is SetThemeEvent.Select -> select(event.themeId, event.context)
        }
    }

    fun select(themeId: Int, context: Context) {
        viewModelScope.launch {
            prefs.saveActiveThemeId(context, themeId)
            prefs.restartActivity(context)
        }
    }

    companion object {
        fun launch(context: Context,
            mainViewModel: MainViewModel
        ) {
            SetThemesViewModel(context,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
            )
        }
    }

}