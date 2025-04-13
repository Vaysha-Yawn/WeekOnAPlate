package week.on.a.plate.dialogs.forSettingsScreen.setTheme.logic


import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.data.preference.PreferenceUseCase
import week.on.a.plate.dialogs.forSettingsScreen.setTheme.event.SetThemeEvent
import week.on.a.plate.dialogs.forSettingsScreen.setTheme.state.SetThemeUIState


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
    private val prefs = PreferenceUseCase

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

    class SetThemesDialogOpenParams(private val context: Context) :
        DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
            SetThemesViewModel(context,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
            )
        }
    }

}