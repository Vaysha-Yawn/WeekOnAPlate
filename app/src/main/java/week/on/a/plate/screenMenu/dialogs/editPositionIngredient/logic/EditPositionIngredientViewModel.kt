package week.on.a.plate.screenMenu.dialogs.editPositionIngredient.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.screenMenu.dialogs.editPositionIngredient.event.EditPositionIngredientEvent
import week.on.a.plate.screenMenu.dialogs.editPositionIngredient.state.EditPositionIngredientUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


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
            state.positionIngredientView?.id?:0,
            IngredientInRecipeView(
                state.positionIngredientView?.id?:0,
                state.ingredientState.value!!,
                state.description.value,
                state.count.doubleValue
            ), state.positionIngredientView?.selectionId?:0
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
            EditPositionIngredientEvent.ChooseIngredient -> TODO()
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