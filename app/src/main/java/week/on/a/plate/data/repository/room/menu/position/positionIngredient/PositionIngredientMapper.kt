package week.on.a.plate.data.repository.room.menu.position.positionIngredient

import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position


class PositionIngredientMapper() {
    fun PositionIngredientRoom.roomToView(
        ingredientInRecipe: IngredientInRecipeView,
    ): Position.PositionIngredientView =
        Position.PositionIngredientView(
            id = positionIngredientId,
            ingredient = ingredientInRecipe,
            selectionId
        )

    fun Position.PositionIngredientView.viewToRoom(
        ingredientInRecipeId: Long,
        selectionId: Long
    ): PositionIngredientRoom =
        PositionIngredientRoom(
            ingredientInRecipeId = ingredientInRecipeId,
            selectionId = selectionId
        )
}
