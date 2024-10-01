package week.on.a.plate.data.repository.tables.shoppingList

import week.on.a.plate.data.dataView.ShoppingItemView
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView


class ShoppingItemMapper() {
    fun ShoppingItemRoom.roomToView(
        ingredientInRecipe: IngredientInRecipeView,
    ): ShoppingItemView =
        ShoppingItemView(
            id = id,
            ingredientInRecipe = ingredientInRecipe,
            checked = checked
        )

    fun ShoppingItemView.viewToRoom(ingredientInRecipeId:Long): ShoppingItemRoom =
        ShoppingItemRoom(
            ingredientInRecipeId = ingredientInRecipeId,
            checked = checked
        )
}
