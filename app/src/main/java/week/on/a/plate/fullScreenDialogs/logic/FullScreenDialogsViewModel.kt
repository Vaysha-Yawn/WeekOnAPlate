package week.on.a.plate.fullScreenDialogs.logic

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.navigation.bottomBar.MenuScreen
import week.on.a.plate.fullScreenDialogs.data.FullScreenDialogsEvent
import week.on.a.plate.menuScreen.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.core.dialogs.logic.DialogManager
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import javax.inject.Inject

@HiltViewModel
class FullScreenDialogsViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel

    fun onEvent(event: Event){
        when(event){
            is MainEvent->{mainViewModel.onEvent(event)}
            is FullScreenDialogsEvent->{onEvent(event)}
        }
    }


    fun onEvent(event: FullScreenDialogsEvent) {
        when (event) {
            is FullScreenDialogsEvent.NavigateToMenuForCreate -> {
                viewModelScope.launch {
                    val selId = sCRUDRecipeInMenu.menuR.getSelIdOrCreate(event.dateToLocalDate, event.categoriesSelection)
                    mainViewModel.nav.previousBackStackEntry?.savedStateHandle?.set(
                        "selId",
                        selId
                    )
                    mainViewModel.nav.popBackStack(
                        route = MenuScreen,
                        inclusive = false, saveState = false
                    )
                }
            }

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
                    event.eventAfter(selId)
                }
            }
        }
    }

}