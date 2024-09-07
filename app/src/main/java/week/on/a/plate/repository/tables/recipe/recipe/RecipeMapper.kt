package week.on.a.plate.repository.tables.recipe.recipe

import week.on.a.plate.core.data.recipe.IngredientInRecipeView
import week.on.a.plate.core.data.recipe.RecipeStepView
import week.on.a.plate.core.data.recipe.RecipeTagView


class RecipeMapper() {
    fun RecipeRoom.roomToView(
        tags: List<RecipeTagView>,
        ingredients: List<IngredientInRecipeView>,
        steps: List<RecipeStepView>
    ): week.on.a.plate.core.data.recipe.RecipeView =
        week.on.a.plate.core.data.recipe.RecipeView(
            id = this.recipeId,
            name = this.name,
            description = this.description,
            img = this.img,
            tags = tags,
            prepTime = this.prepTime,
            allTime = this.allTime,
            standardPortionsCount = this.standardPortionsCount,
            ingredients = ingredients,
            steps = steps,
            link = this.link
        )

    fun week.on.a.plate.core.data.recipe.RecipeView.viewToRoom(): RecipeRoom =
        RecipeRoom(
            name = this.name,
            description = this.description,
            img = this.img,
            prepTime = this.prepTime,
            allTime = this.allTime,
            standardPortionsCount = this.standardPortionsCount,
            link = this.link
        )
}
