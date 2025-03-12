package week.on.a.plate.screens.base.shoppingList.logic

import week.on.a.plate.data.dataView.ShoppingItemView
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.repository.room.shoppingList.ShoppingItemRepository
import javax.inject.Inject

class CheckInListToAddUseCase @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
) {
    suspend operator fun invoke(
        ingredient: IngredientInRecipeView,
    ) {
        val item = shoppingItemRepository.getAll().find { it ->
            it.ingredientInRecipe.ingredientView.ingredientId ==
                    ingredient.ingredientView.ingredientId
        }
        if (item == null) {
            shoppingItemRepository.insert(
                ShoppingItemView(
                    0,
                    ingredient,
                    false
                )
            )
        } else {
            if (item.checked) {
                shoppingItemRepository.update(
                    id = item.id,
                    ingredientInRecipeId = item.ingredientInRecipe.id,
                    checked = false,
                    ingredientId = ingredient.ingredientView.ingredientId,
                    description = item.ingredientInRecipe.description,
                    count = ingredient.count.toDouble()
                )
            } else {
                shoppingItemRepository.update(
                    id = item.id,
                    ingredientInRecipeId = item.ingredientInRecipe.id,
                    checked = false,
                    ingredientId = ingredient.ingredientView.ingredientId,
                    description = item.ingredientInRecipe.description,
                    count = ingredient.count.toDouble() + item.ingredientInRecipe.count.toDouble()
                )
            }
        }
    }
}