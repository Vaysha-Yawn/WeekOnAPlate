package week.on.a.plate.fullScreenDialogs.logic

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.fullScreenDialogs.data.FullScreenDialogsEvent
import week.on.a.plate.menuScreen.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.menuScreen.logic.useCase.DialogManager
import week.on.a.plate.menuScreen.logic.useCase.Navigation
import javax.inject.Inject

@HiltViewModel
class FullScreenDialogsViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val dialogManager: DialogManager,
) : ViewModel() {

    lateinit var snackbarHostState: SnackbarHostState
    lateinit var navController: NavHostController

    fun onEvent(event: FullScreenDialogsEvent) {
        when (event) {
            is FullScreenDialogsEvent.Navigate -> {
                //
            }

            is FullScreenDialogsEvent.OpenDialog -> dialogManager.openDialog(event.dialog, viewModelScope)
            is FullScreenDialogsEvent.CloseDialog -> dialogManager.closeDialog()

            is FullScreenDialogsEvent.ActionMenuBD -> {
                viewModelScope.launch {
                    sCRUDRecipeInMenu.onEvent(
                        event.data, mutableListOf()
                    )
                }
            }
            is FullScreenDialogsEvent.GetSelIdAndCreate -> {
                viewModelScope.launch {
                    val selId = sCRUDRecipeInMenu.menuR.getSelIdOrCreate(event.dateToLocalDate, event.categoriesSelection)
                    event.action(selId)
                }
            }

            is FullScreenDialogsEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    snackbarHostState.showSnackbar(event.messageError)
                }
            }

            is FullScreenDialogsEvent.NavigateBack -> {
                navController.popBackStack()
            }
        }
    }

}