package week.on.a.plate.screens.base.menu.domain.dbusecase

import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import week.on.a.plate.screens.base.menu.domain.repositoryInterface.IRecipeRepository
import javax.inject.Inject


class GetRecipeUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(id: Long): RecipeView {
        return recipeRepository.getRecipe(id)
    }
}

class AddRecipePosToDBUseCase @Inject constructor(
    private val recipeRepository: IRecipeRepository
) {
    suspend operator fun invoke(
        recipe: Position.PositionRecipeView,
        selId: Long
    ) {
        recipeRepository.insert(recipe, selId)
    }
}

class ChangePortionsRecipePosInDBUseCase @Inject constructor(
    private val recipeRepository: IRecipeRepository,
) {
    suspend operator fun invoke(
        recipe: Position.PositionRecipeView,
        count: Int
    ) {
        recipeRepository.update(
            recipe.id,
            recipe.recipe,
            count,
            recipe.selectionId
        )
    }
}

class DeleteRecipePosInDBUseCase @Inject constructor(
    private val recipeRepository: IRecipeRepository
) {
    suspend operator fun invoke(position: Position.PositionRecipeView) {
        recipeRepository.delete(position.idPos)
    }
}