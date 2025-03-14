package week.on.a.plate.screens.additional.recipeDetails.logic


import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import javax.inject.Inject

class SwitchFavoriteUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository,
) {
    suspend operator fun invoke(
        state: RecipeDetailsState,
        update: () -> Unit
    ) {
        state.isFavorite.value = !state.recipe.inFavorite
        recipeRepository.updateRecipeFavorite(
            state.recipe,
            !state.recipe.inFavorite
        )
        update()
    }
}