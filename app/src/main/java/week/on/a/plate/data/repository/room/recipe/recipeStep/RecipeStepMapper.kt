package week.on.a.plate.data.repository.room.recipe.recipeStep

import week.on.a.plate.data.dataView.recipe.RecipeStepView


class RecipeStepMapper() {
    fun RecipeStepRoom.roomToView(): RecipeStepView =
        RecipeStepView(
            id = this.id,
            description = this.description,
            image = this.image,
            timer = this.timer,
            ingredientsPinned
        )

    fun RecipeStepView.viewToRoom(recipeId: Long): RecipeStepRoom =
        RecipeStepRoom(
            recipeId = recipeId,
            description = this.description,
            image = this.image,
            timer = this.timer,
            ingredientsPinnedId
        )
}
