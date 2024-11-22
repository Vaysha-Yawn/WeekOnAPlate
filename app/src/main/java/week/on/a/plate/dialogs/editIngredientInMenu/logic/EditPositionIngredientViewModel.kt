package week.on.a.plate.dialogs.editIngredientInMenu.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editIngredientInMenu.event.EditPositionIngredientEvent
import week.on.a.plate.dialogs.editIngredientInMenu.state.EditPositionIngredientUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.filters.navigation.FilterDestination
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterMode


class EditPositionIngredientViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: EditPositionIngredientUIState
    private var resultFlow: MutableStateFlow<Position.PositionIngredientView?> =
        MutableStateFlow(null)

    fun done() {
        close()
        val newIngredientPosition = Position.PositionIngredientView(
            state.positionIngredientView?.id ?: 0,
            IngredientInRecipeView(
                state.positionIngredientView?.id ?: 0,
                state.ingredientState.value!!,
                state.description.value,
                state.count.intValue
            ), state.positionIngredientView?.selectionId ?: 0
        )
        resultFlow.value = newIngredientPosition
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: EditPositionIngredientEvent) {
        when (event) {
            EditPositionIngredientEvent.Close -> close()
            EditPositionIngredientEvent.Done -> done()
            EditPositionIngredientEvent.ChooseIngredient -> {
                mainViewModel.viewModelScope.launch {
                    chooseIngredient()
                }
            }
            EditPositionIngredientEvent.Delete -> TODO()
        }
    }

    private suspend fun chooseIngredient() {
        mainViewModel.nav.navigate(FilterDestination)
        mainViewModel.onEvent(MainEvent.HideDialog)

        mainViewModel.viewModelScope.launch {
            mainViewModel.filterViewModel.launchAndGet(
                FilterMode.One, FilterEnum.Ingredient,
                Pair(listOf(), listOf()), false
            ) {
                mainViewModel.onEvent(MainEvent.ShowDialog(this@EditPositionIngredientViewModel))
                val new = it.ingredients?.getOrNull(0)
                if (new != null) state.ingredientState.value = new
            }
        }
    }


    suspend fun launchAndGet(
        positionIngredient: Position.PositionIngredientView?, isForAdd: Boolean,
        use: suspend (Position.PositionIngredientView) -> Unit,
    ) {
        state = EditPositionIngredientUIState(positionIngredient)
        mainViewModel.onEvent(MainEvent.OpenDialog(this))

        if (isForAdd) chooseIngredient()

        mainViewModel.viewModelScope.launch {
            val flow: Flow<Position.PositionIngredientView?> = resultFlow
            flow.collect { value ->
                if (value != null) {
                    use(value)
                }
            }
        }
    }

}