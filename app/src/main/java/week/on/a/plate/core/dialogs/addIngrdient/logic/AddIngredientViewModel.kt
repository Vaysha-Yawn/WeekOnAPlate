package week.on.a.plate.core.dialogs.addIngrdient.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.data.recipe.IngredientCategoryView
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.addIngrdient.event.AddIngredientEvent
import week.on.a.plate.core.dialogs.addIngrdient.state.AddIngredientUIState
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.MainViewModel


class AddIngredientViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: AddIngredientUIState
    private lateinit var resultFlow: MutableStateFlow<Pair<IngredientView, IngredientCategoryView>?>

    fun start(): Flow<Pair<IngredientView, IngredientCategoryView>?> {
        val flow = MutableStateFlow<Pair<IngredientView, IngredientCategoryView>?>(null)
        resultFlow = flow
        return flow
    }

    fun done(event: AddIngredientEvent) {
        close()
        //todo
       // resultFlow.value =
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: AddIngredientEvent) {
        when (event) {
            AddIngredientEvent.Close -> close()
            AddIngredientEvent.Done -> done(event)
            AddIngredientEvent.ChooseCategory -> TODO()
        }
    }

    suspend fun launchAndGet(oldIngredient:IngredientView?,oldCategory:IngredientCategoryView?, use: (Pair<IngredientView, IngredientCategoryView>) -> Unit) {
        state = AddIngredientUIState(oldIngredient?.name?:"", oldIngredient?.measure?:"", oldCategory, oldIngredient?.img?:"")

        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}