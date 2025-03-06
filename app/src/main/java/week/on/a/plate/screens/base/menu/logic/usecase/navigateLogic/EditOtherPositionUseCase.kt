package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic

import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.draft.EditDraftOpenDialog
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.ingredient.EditIngredientOpenDialog
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.note.EditNoteOpenDialog
import javax.inject.Inject

class EditOtherPositionUseCase @Inject constructor(
    private val editNoteOpenDialog: EditNoteOpenDialog,
    private val editDraftOpenDialog: EditDraftOpenDialog,
    private val editIngredientOpenDialog: EditIngredientOpenDialog,
) {
    suspend operator fun invoke(
        position: Position,
        mainViewModel: MainViewModel,
    ) {
        when (position) {
            is Position.PositionDraftView -> editDraftOpenDialog(position, mainViewModel)
            is Position.PositionIngredientView -> editIngredientOpenDialog(position, mainViewModel)
            is Position.PositionNoteView -> editNoteOpenDialog(position, mainViewModel)
            is Position.PositionRecipeView -> {}
        }
    }
}