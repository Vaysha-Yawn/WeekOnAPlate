package week.on.a.plate.screens.additional.recipeDetails.logic.dataLogic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import javax.inject.Inject

class EditRecipeUseCaseDB @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        oldRecipe: RecipeView, newRecipe: RecipeView,
    ) = withContext(Dispatchers.IO) {
        recipeRepository.updateRecipe(oldRecipe, newRecipe)
    }
}