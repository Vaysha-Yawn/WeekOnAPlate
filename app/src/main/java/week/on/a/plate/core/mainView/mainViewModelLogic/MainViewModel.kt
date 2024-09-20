package week.on.a.plate.core.mainView.mainViewModelLogic

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.dialogs.logic.DialogManager
import week.on.a.plate.menuScreen.logic.useCase.CRUDRecipeInMenu
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val dialogManager: DialogManager,
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
) : ViewModel() {

    val snackbarHostState = SnackbarHostState()
    lateinit var nav: NavHostController
    val isActiveBaseScreen = mutableStateOf(true)

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> onEvent(event)
            else -> dialogManager.onEvent(event)
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ActionDBMenu -> {
                viewModelScope.launch {
                    sCRUDRecipeInMenu.onEvent(event.actionMenuDBData, listOf())
                }
            }

            MainEvent.CloseDialog -> dialogManager.closeDialog()
            is MainEvent.GetSelIdAndCreate -> TODO()
            is MainEvent.OpenDialog -> dialogManager.openDialog(event.dialog)
            is MainEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    snackbarHostState.showSnackbar(event.message)
                }
            }

            is MainEvent.Navigate -> {
                nav.navigate(event.destination)
            }

            MainEvent.NavigateBack -> {
                nav.popBackStack()
            }
        }
    }
}