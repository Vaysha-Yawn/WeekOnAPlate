package week.on.a.plate.screens.base.shoppingList.logic

import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.repository.tables.shoppingList.ShoppingItemRepository
import javax.inject.Inject

class UpdateCheckUseCase @Inject constructor(
    private val shoppingItemRepository: ShoppingItemRepository,
) {
    suspend operator fun invoke(
        checked: Boolean, ingredientInRecipe: IngredientInRecipeView,
    ) {
        val item = shoppingItemRepository.getAll().find { it ->
            it.ingredientInRecipe.id ==
                    ingredientInRecipe.id
        } ?: return
        shoppingItemRepository.update(
            id = item.id,
            ingredientInRecipeId = item.ingredientInRecipe.id,
            checked = checked,
            ingredientId = item.ingredientInRecipe.ingredientView.ingredientId,
            description = item.ingredientInRecipe.description,
            count = item.ingredientInRecipe.count.toDouble()
        )
    }
}