package week.on.a.plate.screenInventory.logic

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenInventory.event.InventoryEvent
import week.on.a.plate.screenInventory.state.InventoryPositionData
import week.on.a.plate.screenInventory.state.InventoryUIState
import week.on.a.plate.screenSpecifySelection.event.SpecifySelectionEvent
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
) : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    val state: InventoryUIState = InventoryUIState()
    private lateinit var resultFlow: MutableStateFlow<List<IngredientInRecipeView>?>

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> {
                mainViewModel.onEvent(event)
            }

            is SpecifySelectionEvent -> {
                onEvent(event)
            }
        }
    }

    fun onEvent(event: InventoryEvent) {
        when (event) {
            InventoryEvent.Back -> close()
            InventoryEvent.Done -> done()
            is InventoryEvent.PickCount -> {
                event.inventoryCategory.answer.intValue = event.count
            }
        }
    }

    fun done() {
        close()
        resultFlow.value = state.list.value.map { pos ->
           IngredientInRecipeView(
                0, pos.ingredient, "", pos.countTarget-pos.answer.intValue
            )
        }.filter { it.count > 0 }
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.NavigateBack)
    }

    fun start(): Flow<List<IngredientInRecipeView>?> {
        val flow = MutableStateFlow<List<IngredientInRecipeView>?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(
        listStart: List<IngredientInRecipeView>,
        use: (List<IngredientInRecipeView>) -> Unit
    ) {
        state.list.value = listStart.map { InventoryPositionData.getByIngredientInRecipe(it) }

        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}