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
import week.on.a.plate.menuScreen.logic.eventData.DialogMenuData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.menuScreen.logic.eventData.MenuIUState
import week.on.a.plate.menuScreen.logic.eventData.WeekState
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
            // sCRUDRecipeInMenu.menuR.insertNewWeek(WeekDataExample)
            sCRUDRecipeInMenu.menuR.getCurrentWeek(today)
                /* .catch { e->
                     weekState.value = WeekState.Error(e.message.toString()) }*/
                .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), EmptyWeek)
                .collect {
                    if (it == null) {
                        weekState.value = WeekState.EmptyWeek
                    } else {
                        weekState.value = WeekState.Success(it)
                    }
                }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.SwitchWeekOrDayView -> {
                // Логика для переключения между неделей и днем
                menuUIState.itsDayMenu.value = !menuUIState.itsDayMenu.value
                selectedRecipeManager.clear(menuUIState)
            }

            is MenuEvent.AddCheckState -> {
                // Логика для получения состояния
                val id = event.id
                selectedRecipeManager.addNewState(menuUIState, id)
            }

            is MenuEvent.SwitchEditMode -> {
                // Логика переключения режима редактирования
                menuUIState.editing.value = !menuUIState.editing.value
            }

            is MenuEvent.CheckRecipe -> {
                // Логика проверки рецепта по id
                val id = event.id
                selectedRecipeManager.actionCheckRecipe(menuUIState, id)
            }

            is MenuEvent.ChooseAll -> {
                // Логика выбора всех рецептов
                selectedRecipeManager.actionChooseAll(menuUIState)
            }

            is MenuEvent.DeleteSelected -> {
                // Логика удаления выбранных рецептов
                sCRUDRecipeInMenu.actionDeleteSelected(selectedRecipeManager.getSelected(menuUIState))
            }

            is MenuEvent.SelectedToShopList -> {
                // Логика перемещения выбранных рецептов в список покупок
                navigation.actionSelectedToShopList(selectedRecipeManager.getSelected(menuUIState))
            }

            is MenuEvent.AddRecipeToShoppingList -> {}
            is MenuEvent.NavigateFromMenu -> {}

            is MenuEvent.OpenDialog -> {
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
            is MenuEvent.CloseDialog -> {
                menuUIState.dialogState.value = null
            }

            is MenuEvent.ActionDBMenu -> {

            }
        }
    }

    //// Логика навигации к полному рецепту
    //                val recipe = event.rec
    //                selectedRecipeManager.clear(menuUIState)
    //                navigation.actionNavToFullRecipe(recipe)

    @OptIn(ExperimentalMaterial3Api::class)
    fun showBottomDialog(state:SheetState){
        viewModelScope.launch {
            state.show()
        }
    }
}