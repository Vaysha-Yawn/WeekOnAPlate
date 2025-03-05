package week.on.a.plate.screens.base.menu.logic.usecase.dbusecase

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.tables.menu.position.positionRecipe.PositionRecipeRepository
import javax.inject.Inject

class AddRecipePosToDBUseCase @Inject constructor(
    private val recipeRepository: PositionRecipeRepository
) {
    suspend operator fun invoke(
        recipe: Position.PositionRecipeView,
        selId: Long
    ) {
        recipeRepository.insert(recipe, selId)
    }
}

class ChangePortionsRecipePosInDBUseCase @Inject constructor(
    private val recipeRepository: PositionRecipeRepository,
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
    private val recipeRepository: PositionRecipeRepository
) {
    suspend operator fun invoke(position: Position.PositionRecipeView) {
        recipeRepository.delete(position.idPos)
    }
}