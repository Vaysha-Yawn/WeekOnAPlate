package week.on.a.plate.repository.tables.weekOrg.recipeInMenu

import week.on.a.plate.core.data.week.RecipeShortView
import week.on.a.plate.repository.tables.recipe.recipe.Recipe


class RecipeInMenuMapper() {
    fun RecipeInMenuRoom.roomToView(recipeId:Long, name:String): week.on.a.plate.core.data.week.RecipeInMenuView =
        week.on.a.plate.core.data.week.RecipeInMenuView(
            id = this.recipeInMenuId,
            state = this.state,
            recipe = RecipeShortView(recipeId, name) ,
            portionsCount = this.portionsCount
        )

    fun week.on.a.plate.core.data.week.RecipeInMenuView.viewToRoom(selectionId:Long): RecipeInMenuRoom =
        RecipeInMenuRoom(
            state = this.state,
            recipeId = this.recipe.id,
            recipeName = this.recipe.name,
            portionsCount = this.portionsCount,
            selectionId = selectionId
        )
}
