package week.on.a.plate.screenInventory.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.navigation.ShoppingListScreen
import week.on.a.plate.data.dataView.ShoppingItemView
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.repository.tables.shoppingList.ShoppingItemRepository
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenInventory.event.InventoryEvent
import week.on.a.plate.screenInventory.state.InventoryPositionData
import week.on.a.plate.screenInventory.state.InventoryUIState
import week.on.a.plate.screenSpecifySelection.event.SpecifySelectionEvent
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository
) : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    val state: InventoryUIState = InventoryUIState()

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
        val result = state.list.value.map { pos ->
            IngredientInRecipeView(
                0, pos.ingredient, "", pos.countTarget - pos.answer.intValue
            )
        }.filter { it.count > 0 }

        addToBd(result)
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.NavigateBack)
    }

    suspend fun launchAndGet(
        listStart: List<IngredientInRecipeView>
    ) {
        state.list.value = listStart.map { InventoryPositionData.getByIngredientInRecipe(it) }
    }

    private fun addToBd(result: List<IngredientInRecipeView>) {
        viewModelScope.launch {
            val allList = shoppingItemRepository.getAll()
            result.forEach { ingredientInRecipe ->
                val haveItem =
                    allList.find { it -> it.ingredientInRecipe.ingredientView.ingredientId == ingredientInRecipe.ingredientView.ingredientId }
                if (haveItem == null) {
                    shoppingItemRepository.insert(ShoppingItemView(0, ingredientInRecipe, false))
                } else {
                    if (haveItem.checked){
                        shoppingItemRepository.update(
                            haveItem.id,
                            haveItem.ingredientInRecipe.id,
                            false,
                            haveItem.ingredientInRecipe.ingredientView.ingredientId,
                            haveItem.ingredientInRecipe.description,
                            ingredientInRecipe.count.toDouble()
                        )
                    }else{
                        shoppingItemRepository.update(
                            haveItem.id,
                            haveItem.ingredientInRecipe.id,
                            false,
                            haveItem.ingredientInRecipe.ingredientView.ingredientId,
                            haveItem.ingredientInRecipe.description,
                            ingredientInRecipe.count.toDouble() + haveItem.ingredientInRecipe.count.toDouble()
                        )
                    }
                }
            }
            mainViewModel.nav.navigate(ShoppingListScreen)
        }
    }

}