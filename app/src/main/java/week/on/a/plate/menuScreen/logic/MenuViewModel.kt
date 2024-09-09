package week.on.a.plate.menuScreen.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.core.data.example.EmptyWeek
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
    val menuUIState =
        MenuIUState(
            mutableMapOf<Long, MutableState<Boolean>>(),
            mutableStateOf(true),
            mutableStateOf(false),
            mutableIntStateOf(0),
            mutableStateOf(false)
        )

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

            is MenuEvent.NavToFullRecipe -> {
                // Логика навигации к полному рецепту
                val recipe = event.rec
                selectedRecipeManager.clear(menuUIState)
                navigation.actionNavToFullRecipe(recipe)
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

            is MenuEvent.AddRecipeToCategory -> {
                // Логика добавления рецепта в категорию
                val date = event.date
                val category = event.category
                //put in memory date: Int, category: String
                navigation.actionToFindRecipe()
                //sCRUDRecipeInMenu.actionAddRecipeToCategory(date, category)
            }

            is MenuEvent.RecipeToNextStep -> {
                // Логика продвижения рецепта на следующий шаг
                val id = event.id

            }

            is MenuEvent.Edit -> {
                // Логика редактирования рецепта по id
                val id = event.id
                navigation.actionShowEditDialog(id)
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

            is MenuEvent.Search -> {
                //search by draft
            }

            is MenuEvent.AddEmptyDay -> {
                val date = event.data
            }

            is MenuEvent.AddRecipeToShoppingList -> TODO()
            is MenuEvent.Delete -> TODO()
            is MenuEvent.ChangeCount -> TODO()
            is MenuEvent.Double -> TODO()
            is MenuEvent.FindReplace -> TODO()
            is MenuEvent.Move -> TODO()
            is MenuEvent.AddDraftToCategory -> TODO()
            is MenuEvent.AddIngredientToCategory -> TODO()
            is MenuEvent.AddNoteToCategory -> TODO()
        }
    }

    /* fun getCheckState(id: Long): State<Boolean> {
         return selectedRecipeManager.getState(id)
     }*/
}