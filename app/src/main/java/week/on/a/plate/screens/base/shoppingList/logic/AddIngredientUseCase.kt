package week.on.a.plate.screens.base.shoppingList.logic


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.EmptyNavParams
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.navigation.ShoppingListDestination
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.repository.room.shoppingList.ShoppingItemRepository
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.navigation.FilterNavParams
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import javax.inject.Inject

class AddIngredientUseCase @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
    private val checkInListToAdd: CheckInListToAddUseCase,
) {
    suspend operator fun invoke(
        onEvent: (MainEvent) -> Unit,
        scope: CoroutineScope,
        allItemsUnChecked: List<IngredientInRecipeView>
    ) = coroutineScope {
        scope.launch(Dispatchers.Default) {
            val params = FilterNavParams(
                FilterMode.Multiple, FilterEnum.Ingredient, Pair(listOf(),
                    allItemsUnChecked.map { it.ingredientView }), false
            ) { res ->
                onEvent(MainEvent.Navigate(ShoppingListDestination, EmptyNavParams))
                if (res.ingredients == null) return@FilterNavParams
                res.ingredients.forEach {
                    checkInListToAdd(IngredientInRecipeView(0, it, "", 0))
                }
                val startList = allItemsUnChecked.map { it.ingredientView }
                val endList = res.ingredients
                val listToDelete = startList.toMutableList().apply {
                    removeAll(endList)
                }.toList()
                scope.launch(Dispatchers.IO) {
                    listToDelete.forEach { ingredient ->
                        val t = shoppingItemRepository.getAll().find { it ->
                            it.ingredientInRecipe.ingredientView.ingredientId ==
                                    ingredient.ingredientId
                        }
                        if (t != null) shoppingItemRepository.delete(t.id)
                    }
                }
            }
            onEvent(MainEvent.Navigate(FilterDestination, params))
        }
    }
}