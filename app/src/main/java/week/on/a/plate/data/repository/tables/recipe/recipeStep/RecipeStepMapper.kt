package week.on.a.plate.data.repository.tables.recipe.recipeStep

import week.on.a.plate.data.dataView.recipe.RecipeStepView


class RecipeStepMapper() {
    fun RecipeStepRoom.roomToView(): RecipeStepView =
        RecipeStepView(
            id = this.id,
            title = this.title,
            description = this.description,
            image = this.image,
            timer = this.timer
        )

    fun RecipeStepView.viewToRoom(recipeId:Long): RecipeStepRoom =
        RecipeStepRoom(
            recipeId = recipeId,
            title = this.title,
            description = this.description,
            image = this.image,
            timer = this.timer
        )
}
