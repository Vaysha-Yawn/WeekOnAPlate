package week.on.a.plate.screens.base.shoppingList.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.room.shoppingList.ShoppingItemRepository
import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
import javax.inject.Inject

class EditIngredientUseCase @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
    private val checkInListToAdd: CheckInListToAddUseCase,
) {
    suspend operator fun invoke(
        ingredient: IngredientInRecipeView,
        mainViewModel: MainViewModel, scope: CoroutineScope
    ) {
        EditPositionIngredientViewModel.launch(
            Position.PositionIngredientView(
                0,
                ingredient,
                0
            ),
            false,
            mainViewModel,
        ) { updatedIngredient ->
            scope.launch {
                val item = shoppingItemRepository.getAll().find { it ->
                    it.ingredientInRecipe.id ==
                            ingredient.id
                } ?: return@launch
                if (updatedIngredient.ingredient.ingredientView.ingredientId != ingredient.ingredientView.ingredientId) {
                    checkInListToAdd(updatedIngredient.ingredient)
                } else {
                    shoppingItemRepository.update(
                        id = item.id,
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