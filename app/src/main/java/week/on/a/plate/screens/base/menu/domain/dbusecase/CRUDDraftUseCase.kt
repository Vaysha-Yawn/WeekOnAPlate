package week.on.a.plate.screens.base.menu.domain.dbusecase

import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.room.menu.position.draft.PositionDraftRepository
import javax.inject.Inject

class AddDraftToDBUseCase @Inject constructor(
    private val draftRepository: PositionDraftRepository
) {
    suspend operator fun invoke(draft: Position.PositionDraftView, selId: Long) {
        draftRepository.insert(draft, selId)
    }
}

class UpdateDraftInDBUseCase @Inject constructor(
    private val draftRepository: PositionDraftRepository
) {
    suspend operator fun invoke(
        oldDraft: Position.PositionDraftView,
        tags: List<RecipeTagView>, ingredients: List<IngredientView>
    ) {
        draftRepository.update(oldDraft, tags, ingredients)
    }
}

class DeleteDraftInDBUseCase @Inject constructor(
    private val draftRepository: PositionDraftRepository
) {
    suspend operator fun invoke(position: Position.PositionDraftView) {
        draftRepository.delete(position.idPos)
    }
}