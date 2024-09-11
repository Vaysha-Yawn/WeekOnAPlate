package week.on.a.plate.menuScreen.logic

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.core.data.example.EmptyWeek
import week.on.a.plate.core.data.example.WeekDataExample
import week.on.a.plate.menuScreen.logic.eventData.DialogMenuData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.menuScreen.logic.stateData.MenuIUState
import week.on.a.plate.menuScreen.logic.stateData.WeekState
import week.on.a.plate.menuScreen.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.menuScreen.logic.useCase.Navigation
import week.on.a.plate.menuScreen.logic.useCase.SelectedRecipeManager
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val selectedRecipeManager: SelectedRecipeManager,
    private val navigation: Navigation
) : ViewModel() {

    //private val today = LocalDate.now()
    private val today = LocalDate.of(2024, 8, 28)
    val weekState: MutableStateFlow<WeekState> = MutableStateFlow(WeekState.Loading)
    val menuUIState = MenuIUState.MenuIUStateExample

    init {
        viewModelScope.launch {
             //sCRUDRecipeInMenu.menuR.insertNewWeek(WeekDataExample)
            sCRUDRecipeInMenu.menuR.fetWeekFun.getCurrentWeek(today)
                /* .catch { e->
                     weekState.value = WeekState.Error(e.message.toString()) }*/
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), EmptyWeek)
                .collect {
                    weekState.value = WeekState.Success(it)
                }
        }
    }

    fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.SwitchWeekOrDayView -> switchWeekOrDayView()
            is MenuEvent.SwitchEditMode -> switchEditMode()
            is MenuEvent.NavigateFromMenu -> {
                selectedRecipeManager.clear(menuUIState)
                //
            }
            is MenuEvent.OpenDialog -> openDialog(event)
            is MenuEvent.CloseDialog -> menuUIState.dialogState.value = null
            is MenuEvent.ActionDBMenu -> {
                viewModelScope.launch {
                    sCRUDRecipeInMenu.onEvent(event.actionDBData, selectedRecipeManager.getSelected(menuUIState))

                    //
                    sCRUDRecipeInMenu.menuR.fetWeekFun.getCurrentWeek(today)
                        /* .catch { e->
                             weekState.value = WeekState.Error(e.message.toString()) }*/
                        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), EmptyWeek)
                        .collect {
                            weekState.value = WeekState.Success(it)
                        }
                }
            }

            is MenuEvent.ActionSelect -> selectedRecipeManager.onEvent(event.selectedData, menuUIState)
            is MenuEvent.GetSelIdAndCreate -> {
                viewModelScope.launch {
                    val selId = sCRUDRecipeInMenu.menuR.getSelIdOrCreate(event.dateToLocalDate, event.categoriesSelection)
                    event.action(selId)
                }
            }
        }
    }

    private fun switchWeekOrDayView(){
        menuUIState.itsDayMenu.value = !menuUIState.itsDayMenu.value
        selectedRecipeManager.clear(menuUIState)
    }

    private fun switchEditMode(){
        menuUIState.editing.value = !menuUIState.editing.value
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun openDialog(event:MenuEvent.OpenDialog){
        menuUIState.dialogState.value = event.dialog
        when(event.dialog){
            is DialogMenuData.AddIngredient -> showBottomDialog(event.dialog.sheetState)
            is DialogMenuData.AddNote -> showBottomDialog(event.dialog.sheetState)
            is DialogMenuData.ChangePortionsCount -> showBottomDialog(event.dialog.sheetState)
            is DialogMenuData.EditIngredient -> showBottomDialog(event.dialog.sheetState)
            is DialogMenuData.EditNote -> showBottomDialog(event.dialog.sheetState)
            else->{}
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun showBottomDialog(state:SheetState){
        viewModelScope.launch {
            state.show()
        }
    }
}