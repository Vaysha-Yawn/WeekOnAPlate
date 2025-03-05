package week.on.a.plate.screens.base.shoppingList.logic


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.navigation.ShoppingListDestination
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.repository.tables.shoppingList.ShoppingItemRepository
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import javax.inject.Inject

class AddIngredientUseCase @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
    private val checkInListToAdd: CheckInListToAddUseCase,
) {
    suspend operator fun invoke(
        mainViewModel: MainViewModel, scope:CoroutineScope,
        allItemsUnChecked: List<IngredientInRecipeView>
    ) {
        val vm = mainViewModel.filterViewModel
        scope.launch {
            vm.launchAndGet(
                FilterMode.Multiple, FilterEnum.Ingredient, Pair(listOf(),
                    allItemsUnChecked.map { it.ingredientView }), false
            ) { res ->
                mainViewModel.onEvent(MainEvent.Navigate(ShoppingListDestination))
                if (res.ingredients == null) return@launchAndGet
                res.ingredients.forEach {
                    checkInListToAdd(IngredientInRecipeView(0, it, "", 0))
                }
                val startList = allItemsUnChecked.map { it.ingredientView }
                val endList = res.ingredients
                val listToDelete = startList.toMutableList().apply {
                    removeAll(endList)
                }.toList()
                scope.launch {
                    listToDelete.forEach { ingredient ->
                        val t = shoppingItemRepository.getAll().find { it ->
                            it.ingredientInRecipe.ingredientView.ingredientId ==
                                    ingredient.ingredientId
                        }
                        if (t != null) shoppingItemRepository.delete(t.id)
                    }
                }
            }
        }
        mainViewModel.onEvent(MainEvent.Navigate(FilterDestination))
    }
}