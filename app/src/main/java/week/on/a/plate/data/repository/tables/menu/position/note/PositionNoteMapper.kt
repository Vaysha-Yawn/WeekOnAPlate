package week.on.a.plate.data.repository.tables.menu.position.note

import week.on.a.plate.data.dataView.week.Position


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
