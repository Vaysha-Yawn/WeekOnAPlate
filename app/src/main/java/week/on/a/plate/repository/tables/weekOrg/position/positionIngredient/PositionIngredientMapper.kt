package week.on.a.plate.repository.tables.weekOrg.position.positionIngredient

import week.on.a.plate.core.data.recipe.IngredientInRecipeView
import week.on.a.plate.core.data.week.Position


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
