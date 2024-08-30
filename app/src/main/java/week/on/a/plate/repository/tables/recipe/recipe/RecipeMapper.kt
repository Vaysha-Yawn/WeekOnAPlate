package week.on.a.plate.repository.tables.recipe.recipe

import week.on.a.plate.core.data.recipe.IngredientInRecipe
import week.on.a.plate.core.data.recipe.RecipeStep
import week.on.a.plate.core.data.recipe.RecipeTag


class RecipeMapper() {
    fun Recipe.roomToView(
        tags: List<RecipeTag>,
        ingredients: List<IngredientInRecipe>,
        steps: List<RecipeStep>
    ): week.on.a.plate.core.data.recipe.RecipeView =
        week.on.a.plate.core.data.recipe.RecipeView(
            id = this.recipeId,
            name = this.name,
            img = this.img,
            tags = tags,
            prepTime = this.prepTime,
            allTime = this.allTime,
            standardPortionsCount = this.standardPortionsCount,
            ingredients = ingredients,
            steps = steps,
            link = this.link
        )

    fun week.on.a.plate.core.data.recipe.RecipeView.viewToRoom(): Recipe =
        Recipe(
            recipeId = this.id,
            name = this.name,
            img = this.img,
            prepTime = this.prepTime,
            allTime = this.allTime,
            standardPortionsCount = this.standardPortionsCount,
            link = this.link
        )
}
