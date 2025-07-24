package week.on.a.plate.screens.additional.createRecipe.logic.useCase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import java.time.LocalDateTime
import javax.inject.Inject

class EditRecipeUseCaseDB @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        recipe: RecipeCreateUIState, oldRecipe: RecipeView
    ) = withContext(Dispatchers.IO) {
        val newRecipe = RecipeView(
            id = oldRecipe.id,
            name = recipe.name.value,
            description = recipe.description.value,
            img = recipe.photoLink.value,
            tags = recipe.tags.value,
            standardPortionsCount = recipe.portionsCount.intValue,
            ingredients = recipe.ingredients.value,
            steps = recipe.steps.value.map {
                RecipeStepView(
                    it.id,
                    it.description.value,
                    it.image.value,
                    it.timer.longValue, it.pinnedIngredientsInd.value
                )
            },
            link = recipe.link.value,
            oldRecipe.inFavorite,
            LocalDateTime.now(),
            recipe.duration.value
        )

        recipeRepository.updateRecipe(oldRecipe, newRecipe)
    }
}