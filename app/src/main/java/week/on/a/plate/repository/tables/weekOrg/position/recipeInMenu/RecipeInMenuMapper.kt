package week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu

import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.RecipeShortView


class RecipeInMenuMapper() {
    fun PositionRecipeRoom.roomToView(recipeId:Long, name:String): Position.PositionRecipeView =
        Position.PositionRecipeView(
            id = this.recipeInMenuId,
            recipe = RecipeShortView(recipeId, name) ,
            portionsCount = this.portionsCount
        )

    fun Position.PositionRecipeView.viewToRoom(selectionId:Long): PositionRecipeRoom =
        PositionRecipeRoom(
            recipeId = this.recipe.id,
            recipeName = this.recipe.name,
            portionsCount = this.portionsCount,
            selectionId = selectionId
        )
}
