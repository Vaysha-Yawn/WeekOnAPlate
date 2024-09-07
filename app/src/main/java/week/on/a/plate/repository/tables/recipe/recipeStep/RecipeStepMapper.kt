package week.on.a.plate.repository.tables.recipe.recipeStep


class RecipeStepMapper() {
    fun RecipeStepRoom.roomToView(): week.on.a.plate.core.data.recipe.RecipeStepView =
        week.on.a.plate.core.data.recipe.RecipeStepView(
            id = this.id,
            title = this.title,
            description = this.description,
            image = this.image,
            timer = this.timer
        )

    fun week.on.a.plate.core.data.recipe.RecipeStepView.viewToRoom(recipeId:Long): RecipeStepRoom =
        RecipeStepRoom(
            recipeId = recipeId,
            title = this.title,
            description = this.description,
            image = this.image,
            timer = this.timer
        )
}
