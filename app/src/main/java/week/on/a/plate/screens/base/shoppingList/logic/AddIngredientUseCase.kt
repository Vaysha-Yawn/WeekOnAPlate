package week.on.a.plate.screens.base.shoppingList.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.navigation.ShoppingListDestination
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.repository.room.shoppingList.ShoppingItemRepository
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import week.on.a.plate.screens.additional.filters.state.FilterResult
import javax.inject.Inject


class AddIngredientUseCase @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
    private val checkInListToAdd: CheckInListToAddUseCase,
) {
    operator fun invoke(
        onEvent: (MainEvent) -> Unit,
        scope: CoroutineScope,
        allItemsUnChecked: List<IngredientInRecipeView>
    ) {
        onEvent(
            MainEvent.Navigate(
                FilterDestination(
                    FilterMode.Multiple,
                    FilterEnum.Ingredient,
                    Pair(
                        listOf(),
                        allItemsUnChecked.map { it.ingredientView }),
                    false
                )
            )
        )
    }

    //todo use
    jyyjyjy
    suspend fun afterResult(
        onEvent: (MainEvent) -> Unit,
        allItemsUnChecked: List<IngredientInRecipeView>,
        res: FilterResult
    ) = coroutineScope {
        if (res.ingredients == null) return@coroutineScope
        res.ingredients.forEach {
            checkInListToAdd(IngredientInRecipeView(0, it, "", 0))
        }
        val startList = allItemsUnChecked.map { it.ingredientView }
        val endList = res.ingredients
        val listToDelete = startList.toMutableList().apply {
            removeAll(endList)
        }.toList()
        launch(Dispatchers.IO) {
            listToDelete.forEach { ingredient ->
                val t = shoppingItemRepository.getAll().find { it ->
                    it.ingredientInRecipe.ingredientView.ingredientId ==
                            ingredient.ingredientId
                }
                if (t != null) shoppingItemRepository.delete(t.id)
            }
        }
        onEvent(MainEvent.Navigate(ShoppingListDestination))
    }
}
