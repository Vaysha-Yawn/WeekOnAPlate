package week.on.a.plate.dialogs.editIngredientInMenu.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editIngredientInMenu.event.EditPositionIngredientEvent
import week.on.a.plate.dialogs.editIngredientInMenu.state.EditPositionIngredientUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.filters.navigation.FilterDestination
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterMode


class EditPositionIngredientViewModel(
    positionIngredient: Position.PositionIngredientView?, isForAdd: Boolean,
    use: suspend (Position.PositionIngredientView) -> Unit,
) : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    var state: EditPositionIngredientUIState = EditPositionIngredientUIState(positionIngredient)
    private var resultFlow: MutableStateFlow<Position.PositionIngredientView?> =
        MutableStateFlow(null)

    init {
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
            EditPositionIngredientEvent.ChooseIngredient -> chooseIngredient()
        }
    }

    private fun chooseIngredient() {
        mainViewModel.viewModelScope.launch {
            mainViewModel.nav.navigate(FilterDestination)
            mainViewModel.onEvent(MainEvent.HideDialog)

            mainViewModel.viewModelScope.launch {
                mainViewModel.filterViewModel.launchAndGet(
                    FilterMode.One, FilterEnum.Ingredient,
                    Pair(listOf(), listOf()), false
                ) {
                    mainViewModel.onEvent(MainEvent.ShowDialog)
                    val new = it.ingredients?.getOrNull(0)
                    if (new != null) state.ingredientState.value = new
                }
            }
        }
    }

}