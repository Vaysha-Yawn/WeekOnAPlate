package week.on.a.plate.screens.additional.recipeDetails.logic.dataLogic


import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import javax.inject.Inject

class SwitchFavoriteUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository,
) {
    suspend operator fun invoke(
        recipe: RecipeView
    ) {
        recipeRepository.updateRecipeFavorite(
            recipe,
            !recipe.inFavorite
        )
    }
}