package week.on.a.plate.screens.base.menu.logic.usecase.dbusecase

import week.on.a.plate.data.dataView.week.Position
import javax.inject.Inject

class NoteMovePositionInMenuDB @Inject constructor(
    private val noteAdd: AddNoteToDBUseCase,
    private val noteDelete: DeleteNoteInDBUseCase,
) {
    suspend operator fun invoke(
        position: Position.PositionNoteView, selId: Long
    ) {
        noteDelete(position)
        noteAdd(position.note, selId)
    }
}

class DraftMovePositionInMenuDB @Inject constructor(
    private val draftAdd: AddDraftToDBUseCase,
    private val draftDelete: DeleteDraftInDBUseCase,
) {
    suspend operator fun invoke(
        position: Position.PositionDraftView, selId: Long
    ) {
        draftDelete(position)
        draftAdd(position, selId)
    }
}

class RecipeMovePositionInMenuDB @Inject constructor(
    private val recipeAdd: AddRecipePosToDBUseCase,
    private val recipeDelete: DeleteRecipePosInDBUseCase
) {
    suspend operator fun invoke(
        position: Position.PositionRecipeView, selId: Long
    ) {
        recipeDelete(position)
        recipeAdd(position, selId)
    }
}

class IngredientMovePositionInMenuDB @Inject constructor(
    private val ingredientAdd: AddIngredientPositionToDBUseCase,
    private val ingredientDelete: DeleteIngredientPositionInDBUseCase
) {
    suspend operator fun invoke(
        position: Position.PositionIngredientView, selId: Long
    ) {
        ingredientDelete(position)
        ingredientAdd(position, selId)
    }
}