package week.on.a.plate.screens.additional.recipeDetails.logic.dataLogic

import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import javax.inject.Inject

class DeleteUseCaseDB @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        state: RecipeDetailsState,
    ) {
        recipeRepository.delete(state.recipe.id)
    }
}