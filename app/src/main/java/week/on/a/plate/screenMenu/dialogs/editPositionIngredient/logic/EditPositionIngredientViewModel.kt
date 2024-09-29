package week.on.a.plate.screenMenu.dialogs.editPositionIngredient.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.screenMenu.dialogs.editPositionIngredient.event.EditPositionIngredientEvent
import week.on.a.plate.screenMenu.dialogs.editPositionIngredient.state.EditPositionIngredientUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenFilters.navigation.FilterDestination
import week.on.a.plate.screenFilters.state.FilterMode


class EditPositionIngredientViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: EditPositionIngredientUIState
    private lateinit var resultFlow: MutableStateFlow<Position.PositionIngredientView?>

    fun start(): Flow<Position.PositionIngredientView?> {
        val flow = MutableStateFlow<Position.PositionIngredientView?>(null)
        resultFlow = flow
        return flow
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
            mainViewModel.onEvent(MainEvent.HideDialog)
            mainViewModel.nav.navigate(FilterDestination)
            val listLast = if (state.ingredientState.value != null) {
                listOf(state.ingredientState.value!!)
            } else listOf()
            mainViewModel.filterViewModel.launchAndGet(
                FilterMode.OneIngredient,
                Pair(listOf(), listLast)
            ) {
                mainViewModel.onEvent(MainEvent.ShowDialog(this@EditPositionIngredientViewModel))
                state.ingredientState.value = it.second.getOrNull(0)
            }
        }
    }


    suspend fun launchAndGet(
        positionIngredient: Position.PositionIngredientView?,
        use: (Position.PositionIngredientView) -> Unit
    ) {
        state = EditPositionIngredientUIState(positionIngredient)
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}