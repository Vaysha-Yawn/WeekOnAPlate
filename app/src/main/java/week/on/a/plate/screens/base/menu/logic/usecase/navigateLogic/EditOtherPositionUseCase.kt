package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic

import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.draft.EditDraft
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.ingredient.EditIngredient
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.note.EditNote
import javax.inject.Inject

class EditOtherPositionUseCase @Inject constructor(
    private val editNote: EditNote,
    private val editDraft: EditDraft,
    private val editIngredient: EditIngredient,
) {
    suspend operator fun invoke(
        position: Position,
        mainViewModel: MainViewModel,
    ) {
        when (position) {
            is Position.PositionDraftView -> editDraft(position, mainViewModel)
            is Position.PositionIngredientView -> editIngredient(position, mainViewModel)
            is Position.PositionNoteView -> editNote(position, mainViewModel)
            is Position.PositionRecipeView -> {}
        }
    }
}