package week.on.a.plate.screens.base.menu.logic.usecase.dbusecase

import week.on.a.plate.data.dataView.week.Position
import javax.inject.Inject

class IngredientDoublePositionInMenuDB @Inject constructor(
    private val ingredientAdd: AddIngredientPositionToDBUseCase
) {
    suspend operator fun invoke(
        position: Position.PositionIngredientView, selId: Long
    ) {
        ingredientAdd(position, selId)
    }
}

class RecipeDoublePositionInMenuDB @Inject constructor(
    private val recipeAdd: AddRecipePosToDBUseCase,
) {
    suspend operator fun invoke(
        position: Position.PositionRecipeView, selId: Long
    ) {
        recipeAdd(position, selId)
    }
}

class DraftDoublePositionInMenuDB @Inject constructor(
    private val draftAdd: AddDraftToDBUseCase,
) {
    suspend operator fun invoke(
        position: Position.PositionDraftView, selId: Long
    ) {
        draftAdd(position, selId)
    }
}

class NoteDoublePositionInMenuDB @Inject constructor(
    private val noteAdd: AddNoteToDBUseCase,
) {
    suspend operator fun invoke(
        position: Position.PositionNoteView, selId: Long
    ) {
        noteAdd(position.note, selId)
    }
}

