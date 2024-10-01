package week.on.a.plate.screenShoppingList.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.ShoppingItemView
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.tables.shoppingList.ShoppingItemRepository
import week.on.a.plate.dialogEditPositionIngredient.logic.EditPositionIngredientViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenShoppingList.event.ShoppingListEvent
import week.on.a.plate.screenShoppingList.state.ShoppingListUIState
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
    ) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = ShoppingListUIState()

    lateinit var allItemsChecked: StateFlow<List<IngredientInRecipeView>>
    lateinit var allItemsUnChecked: StateFlow<List<IngredientInRecipeView>>

    init {
        viewModelScope.launch {
            allItemsChecked = shoppingItemRepository.getCheckedFlow().map { viewList -> viewList.map {
                    shoppingItemView->shoppingItemView.ingredientInRecipe } }.stateIn(viewModelScope)
            allItemsUnChecked = shoppingItemRepository.getUnCheckedFlow().map { viewList -> viewList.map {
                    shoppingItemView->shoppingItemView.ingredientInRecipe } }.stateIn(viewModelScope)
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> mainViewModel.onEvent(event)
            else -> onEvent(event)
        }
    }

    fun onEvent(event: ShoppingListEvent) {
        when (event) {
            ShoppingListEvent.Add -> addIngredient()
            is ShoppingListEvent.Check -> {
                viewModelScope.launch {
                    val item = allItemsUnChecked.value.find { it -> it == event.position }
                        ?: return@launch
                    shoppingItemRepository.update(
                        id = item.id,
                        ingredientInRecipeId = item.id,
                        checked = true,
                        ingredientId = item.ingredientView.ingredientId,
                        description = item.description,
                        count = item.count.toDouble()
                    )
                }
            }

            ShoppingListEvent.DeleteChecked -> {
                viewModelScope.launch {
                    shoppingItemRepository.deleteAllChecked()
                }
            }

            is ShoppingListEvent.Uncheck -> {
                viewModelScope.launch {
                    val item = allItemsChecked.value.find { it -> it == event.position }
                        ?: return@launch
                    shoppingItemRepository.update(
                        id = item.id,
                        ingredientInRecipeId = item.id,
                        checked = false,
                        ingredientId = item.ingredientView.ingredientId,
                        description = item.description,
                        count = item.count.toDouble()
                    )
                }
            }

            is ShoppingListEvent.Edit -> {
                editIngredient(event.ingredient)
            }
        }
    }

    private fun editIngredient(ingredient: IngredientInRecipeView) {
        viewModelScope.launch {
            val vm = EditPositionIngredientViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(
                Position.PositionIngredientView(
                    0,
                    ingredient,
                    0
                )
            ) { updatedIngredient ->
                viewModelScope.launch {
                    if (updatedIngredient.ingredient.ingredientView.ingredientId != ingredient.ingredientView.ingredientId) {
                        checkInListToAdd(updatedIngredient.ingredient)
                    } else {
                        shoppingItemRepository.update(
                            id = ingredient.id,
                            ingredientInRecipeId = ingredient.id,
                            checked = false,
                            ingredientId = updatedIngredient.ingredient.ingredientView.ingredientId,
                            description = updatedIngredient.ingredient.description,
                            count = updatedIngredient.ingredient.count.toDouble()
                        )
                    }
                }
            }
        }
    }

    private suspend fun checkInListToAdd(ingredient: IngredientInRecipeView){
        val item = shoppingItemRepository.getAll().find { it -> it.ingredientInRecipe.ingredientView.ingredientId ==
                    ingredient.ingredientView.ingredientId }

        if (item == null) {
            shoppingItemRepository.insert(
                ShoppingItemView(
                    0,
                    ingredient,
                    false
                )
            )
        } else {
            mainViewModel.onEvent(MainEvent.ShowSnackBar("Этот ингредиент уже добавлен в список покупок поэтому колличество добавилось"))
            if (item.checked) {
                shoppingItemRepository.update(
                    id = item.id,
                    ingredientInRecipeId = item.ingredientInRecipe.id,
                    checked = false,
                    ingredientId = ingredient.ingredientView.ingredientId,
                    description = ingredient.description,
                    count = ingredient.count.toDouble()
                )
            } else {
                shoppingItemRepository.update(
                    id = item.id,
                    ingredientInRecipeId = item.ingredientInRecipe.id,
                    checked = false,
                    ingredientId = ingredient.ingredientView.ingredientId,
                    description = ingredient.description,
                    count = ingredient.count.toDouble() + item.ingredientInRecipe.count.toDouble()
                )
            }
        }
    }

    private fun addIngredient() {
        viewModelScope.launch {
            val vm = EditPositionIngredientViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(null) { updatedIngredient ->
                viewModelScope.launch {
                    checkInListToAdd(updatedIngredient.ingredient)
                }
            }
        }
    }
}