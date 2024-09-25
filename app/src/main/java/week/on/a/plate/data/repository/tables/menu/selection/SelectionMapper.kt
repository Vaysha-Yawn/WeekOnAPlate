package week.on.a.plate.data.repository.tables.menu.selection

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView


class SelectionMapper() {
    fun SelectionRoom.roomToView(
        positions: MutableList<Position>,
    ): SelectionView =
        SelectionView(
            id = this.selectionId,
            category = this.category,
            positions = positions
        )

    fun SelectionView.viewToRoom(dayId: Long): SelectionRoom =
        SelectionRoom(
            dayId = dayId,
            category = this.category
        )
}
