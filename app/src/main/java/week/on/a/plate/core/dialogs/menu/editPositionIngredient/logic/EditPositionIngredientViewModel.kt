package week.on.a.plate.core.dialogs.menu.editPositionIngredient.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.data.recipe.IngredientInRecipeView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.dialogAbstract.event.DialogEvent
import week.on.a.plate.core.dialogs.menu.editPositionIngredient.event.EditPositionIngredientEvent
import week.on.a.plate.core.dialogs.menu.editPositionIngredient.state.EditPositionIngredientUIState
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel


class EditPositionIngredientViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: EditPositionIngredientUIState
    private lateinit var resultFlow: MutableStateFlow<Position.PositionIngredientView?>
    var oldPositionIngredient: Position.PositionIngredientView? = null

    fun start(): Flow<Position.PositionIngredientView?> {
        val flow = MutableStateFlow<Position.PositionIngredientView?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        val newIngredientPosition = Position.PositionIngredientView(
            oldPositionIngredient?.id?:0,
            IngredientInRecipeView(
                oldPositionIngredient?.id?:0,
                state.ingredientState.value!!,
                state.description.value,
                state.count.doubleValue
            ), oldPositionIngredient?.selectionId?:0
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
        oldPositionIngredient = positionIngredient
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}