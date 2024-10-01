package week.on.a.plate.data.dataView

import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView

data class ShoppingItemView(
    var id: Long = 0,
    val ingredientInRecipe: IngredientInRecipeView,
    val checked: Boolean,
)