package week.on.a.plate.screens.additional.createRecipe.logic.useCase

import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import java.time.LocalDateTime
import javax.inject.Inject

class CreateRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        recipe: RecipeCreateUIState
    ) {
        val newRecipe = getRecipeFromCreateState(recipe)
        recipeRepository.create(newRecipe)
    }

    private fun getRecipeFromCreateState(recipe: RecipeCreateUIState): RecipeView {
        val newRecipe = RecipeView(
            id = 0,
            name = recipe.name.value,
            description = recipe.description.value,
            img = recipe.photoLink.value,
            tags = recipe.tags.value,
            standardPortionsCount = recipe.portionsCount.intValue,
            ingredients = recipe.ingredients.value,
            steps = recipe.steps.value.map {
                RecipeStepView(
                    0,
                    it.description.value,
                    it.image.value,
                    it.timer.longValue, it.pinnedIngredientsInd.value
                )
            },
            link = recipe.link.value,
            false,
            LocalDateTime.now(),
            recipe.duration.value
        )
        return newRecipe
    }
}