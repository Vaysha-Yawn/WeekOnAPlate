package week.on.a.plate.data.repository.tables.recipe.recipe

import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.RecipeView


class RecipeMapper() {
    fun RecipeRoom.roomToView(
        tags: List<RecipeTagView>,
        ingredients: List<IngredientInRecipeView>,
        steps: List<RecipeStepView>
    ): RecipeView =
        RecipeView(
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
            link = this.link,
            inFavorite = this.inFavorite,
            lastEdit = lastEdit
        )

    fun RecipeView.viewToRoom(): RecipeRoom =
        RecipeRoom(
            name = this.name,
            description = this.description,
            img = this.img,
            prepTime = this.prepTime,
            allTime = this.allTime,
            standardPortionsCount = this.standardPortionsCount,
            link = this.link,
            inFavorite = this.inFavorite,
            lastEdit = lastEdit
        )
}
