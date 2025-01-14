package week.on.a.plate.screens.menu.logic.useCase

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.MenuEvent
import week.on.a.plate.screens.menu.logic.useCase.crudPositions.draft.EditDraft
import week.on.a.plate.screens.menu.logic.useCase.crudPositions.ingredient.EditIngredient
import week.on.a.plate.screens.menu.logic.useCase.crudPositions.note.EditNote
import javax.inject.Inject

class EditOtherPositionUseCase @Inject constructor(
    private val editNote: EditNote,
    private val editDraft: EditDraft,
    private val editIngredient: EditIngredient,
) {
    suspend operator fun invoke(
        position: Position,
        mainViewModel: MainViewModel,
        onEvent:(MenuEvent)->Unit
    ) {
        when (position) {
            is Position.PositionDraftView ->
                editDraft(position, mainViewModel, onEvent)

            is Position.PositionIngredientView ->
                editIngredient(position, mainViewModel, onEvent)

            is Position.PositionNoteView ->
                editNote(position, mainViewModel, onEvent)

            is Position.PositionRecipeView -> {}
        }
    }
}