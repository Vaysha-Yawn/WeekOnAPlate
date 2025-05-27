package week.on.a.plate.screens.additional.inventory.logic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.EmptyNavParams
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.navigation.ShoppingListDestination
import week.on.a.plate.data.dataView.ShoppingItemView
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.repository.room.shoppingList.ShoppingItemRepository
import week.on.a.plate.screens.additional.inventory.event.InventoryEvent
import week.on.a.plate.screens.additional.inventory.state.InventoryPositionData
import week.on.a.plate.screens.additional.inventory.state.InventoryUIState
import week.on.a.plate.screens.additional.specifySelection.event.SpecifySelectionEvent
import javax.inject.Inject


@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository
) : ViewModel() {
    val state: InventoryUIState = InventoryUIState()

    val dialogOpenParams = mutableStateOf<DialogOpenParams?>(null)
    val mainEvent = mutableStateOf<MainEvent?>(null)

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> {
                mainEvent.value = event
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
                event.inventoryCategory.answer.value = ! event.inventoryCategory.answer.value
            }
        }
    }

    fun done() {
        close()
        val result = state.list.value.filter { it.answer.value }.map { pos ->
            IngredientInRecipeView(
                0, pos.ingredient, "", pos.countTarget
            )
        }
        addToBd(result)
    }

    fun close() {
        mainEvent.value = MainEvent.NavigateBack
    }

    fun launchAndGet(
        listStart: List<IngredientInRecipeView>
    ) {
        state.list.value = listStart.map { InventoryPositionData.getByIngredientInRecipe(it) }
    }

    fun launchAndGetMore(
        listsStart: List<IngredientInRecipeView>
    ) {
        val listResult = mutableMapOf<Long, IngredientInRecipeView>()
        listsStart.forEach {
            if (listResult[it.ingredientView.ingredientId]!=null){
                listResult[it.ingredientView.ingredientId]!!.count = listResult[it.ingredientView.ingredientId]!!.count.plus(it.count)
                listResult[it.ingredientView.ingredientId]!!.description = ""
            }else{
                listResult[it.ingredientView.ingredientId] = it
                listResult[it.ingredientView.ingredientId]!!.description = ""
            }
        }
        state.list.value = listResult.values.map { InventoryPositionData.getByIngredientInRecipe(it) }
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
            mainEvent.value = MainEvent.Navigate(ShoppingListDestination, EmptyNavParams)
        }
    }

}