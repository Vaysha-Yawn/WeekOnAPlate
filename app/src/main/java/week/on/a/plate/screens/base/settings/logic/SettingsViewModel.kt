package week.on.a.plate.screens.base.settings.logic

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.preference.PreferenceUseCase
import week.on.a.plate.data.repository.room.menu.category_selection.CategorySelectionDAO
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.dialogs.forSettingsScreen.setPermanentMeals.logic.SetPermanentMealsViewModel
import week.on.a.plate.dialogs.forSettingsScreen.setTheme.logic.SetThemesViewModel
import week.on.a.plate.screens.additional.ppAndTermsOfUse.navigation.DocumentsWebDestination
import week.on.a.plate.screens.base.settings.event.SettingsEvent
import week.on.a.plate.screens.base.settings.state.SettingsUIState
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val dao: CategorySelectionDAO) : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    val state = SettingsUIState()
    private val pref = PreferenceUseCase

    val dialogOpenParams = mutableStateOf<DialogOpenParams?>(null)
    val mainEvent = mutableStateOf<MainEvent?>(null)

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> {
                mainEvent.value = event
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
            is SettingsEvent.SetMenuSelections -> setMenuSelections()
            is SettingsEvent.SetStdPortionsCount -> setStdPortionsCount(event.context)
            is SettingsEvent.Theme -> theme(event.context)
            is SettingsEvent.Tutorial -> tutorial(event.context)
            SettingsEvent.TermsOfUse -> termsOfUse()
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
        mainViewModel.documentsWebViewModel.launch(true)
        mainViewModel.nav.navigate(DocumentsWebDestination)
    }

    private fun termsOfUse() {
        mainViewModel.documentsWebViewModel.launch(false)
        mainViewModel.nav.navigate(DocumentsWebDestination)
    }

    private fun profile(context: Context) {

    }

    private fun rateApp(context: Context) {

    }

    private fun setMenuSelections() {
        dialogOpenParams.value = SetPermanentMealsViewModel.SetPermanentMealsDialogParams(dao)
    }

    private fun setStdPortionsCount(context: Context) {
        val current = pref.getDefaultPortionsCount(context)
        val params =
            ChangePortionsCountViewModel.ChangePortionsCountDialogParams(current) { count ->
                pref.saveDefaultPortionsCount(context, count)
            }
        dialogOpenParams.value = params
    }

    private fun theme(context: Context) {
        dialogOpenParams.value = SetThemesViewModel.SetThemesDialogOpenParams(context)
    }

    private fun tutorial(context: Context) {

    }

}