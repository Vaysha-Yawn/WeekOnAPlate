package week.on.a.plate.repository.tables.weekOrg.position.positionNote

import week.on.a.plate.core.data.recipe.IngredientInRecipeView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientRoom


class PositionNoteMapper() {
    fun PositionNoteRoom.roomToView(): Position.PositionNoteView =
        Position.PositionNoteView(
            id = this.id,
            note = this.note,
            selectionId = this.selectionId
        )

    fun Position.PositionNoteView.viewToRoom(selectionId:Long): PositionNoteRoom =
        PositionNoteRoom(
            note = this.note,
            selectionId = selectionId
        )
}
