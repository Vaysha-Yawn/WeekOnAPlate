package week.on.a.plate.screens.base.menu.domain.utilsUseCase

import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeView
import javax.inject.Inject

class IngredientsMapByPortionsUseCase @Inject constructor() {

    operator fun invoke(currentPortions: Int, recipe: RecipeView): List<IngredientInRecipeView> {
        val standardCount = recipe.standardPortionsCount
        return recipe.ingredients.map { ingredientInRecipeView ->
            ingredientInRecipeView.count =
                ingredientInRecipeView.count / standardCount * currentPortions
            ingredientInRecipeView
        }
    }
}