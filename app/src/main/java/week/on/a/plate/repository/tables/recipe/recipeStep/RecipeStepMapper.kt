package week.on.a.plate.repository.tables.recipe.recipeStep


class RecipeStepMapper() {
    fun RecipeStep.roomToView(): week.on.a.plate.core.data.recipe.RecipeStepView =
        week.on.a.plate.core.data.recipe.RecipeStepView(
            id = this.id,
            title = this.title,
            description = this.description,
            image = this.image
        )

    fun week.on.a.plate.core.data.recipe.RecipeStepView.viewToRoom(recipeId:Long): RecipeStep =
        RecipeStep(
            id = this.id,
            recipeId = recipeId,
            title = this.title,
            description = this.description,
            image = this.image
        )
}
