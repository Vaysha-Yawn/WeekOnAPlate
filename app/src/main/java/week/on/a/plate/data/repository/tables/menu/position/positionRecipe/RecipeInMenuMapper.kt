package week.on.a.plate.data.repository.tables.menu.position.positionRecipe

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView


class RecipeInMenuMapper() {
    fun PositionRecipeRoom.roomToView(recipeId:Long, name:String, img:String,): Position.PositionRecipeView =
        Position.PositionRecipeView(
            id = this.recipeInMenuId,
            recipe = RecipeShortView(recipeId, name, img) ,
            portionsCount = this.portionsCount,
            this.selectionId
        )

    fun Position.PositionRecipeView.viewToRoom(selectionId:Long): PositionRecipeRoom =
        PositionRecipeRoom(
            recipeId = this.recipe.id,
            portionsCount = this.portionsCount,
            selectionId = selectionId,
        )
}
