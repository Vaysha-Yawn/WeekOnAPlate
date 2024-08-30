package week.on.a.plate.repository.tables.weekOrg.recipeInMenu

import week.on.a.plate.core.data.recipe.RecipeView
import week.on.a.plate.core.data.week.SelectionInDayData
import week.on.a.plate.repository.tables.recipe.recipe.Recipe


class RecipeInMenuMapper() {
    fun RecipeInMenu.roomToView(recipe: RecipeView): week.on.a.plate.core.data.week.RecipeInMenu =
        week.on.a.plate.core.data.week.RecipeInMenu(
            id = this.recipeInMenuId,
            state = this.state,
            recipe = recipe,
            portionsCount = this.portionsCount
        )

    fun week.on.a.plate.core.data.week.RecipeInMenu.viewToRoom(selectionId:Long): RecipeInMenu =
        RecipeInMenu(
            recipeInMenuId = this.id,
            state = this.state,
            recipeId = this.recipe.id,
            portionsCount = this.portionsCount,
            selectionId = selectionId
        )
}
