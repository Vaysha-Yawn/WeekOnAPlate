package week.on.a.plate.data.repository.tables.menu.position.positionIngredient

import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position


class PositionIngredientMapper() {
    fun PositionIngredientRoom.roomToView(
        ingredientInRecipe: IngredientInRecipeView,
    ): Position.PositionIngredientView =
        Position.PositionIngredientView(
            id = this.positionIngredientId,
            ingredient = ingredientInRecipe,
            this.selectionId
        )

    fun Position.PositionIngredientView.viewToRoom(selectionId:Long): PositionIngredientRoom =
        PositionIngredientRoom(
            ingredientInRecipeId = this.ingredient.id,
            selectionId = selectionId
        )
}
