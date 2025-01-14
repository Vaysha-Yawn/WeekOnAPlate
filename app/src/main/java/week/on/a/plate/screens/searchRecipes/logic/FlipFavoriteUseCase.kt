package week.on.a.plate.screens.searchRecipes.logic

import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import javax.inject.Inject

class FlipFavoriteUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository,
) {
    suspend operator fun invoke(
        recipe: RecipeView, inFavorite: Boolean
    ) {
        recipeRepository.updateRecipeFavorite(recipe, !inFavorite)
    }
}