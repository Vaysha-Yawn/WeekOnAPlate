package week.on.a.plate.repository.tables.weekOrg.selectionInDay

import week.on.a.plate.core.data.week.Position


class SelectionMapper() {
    fun SelectionRoom.roomToView(
        positions: MutableList<Position>,
    ): week.on.a.plate.core.data.week.SelectionView =
        week.on.a.plate.core.data.week.SelectionView(
            id = this.selectionId,
            category = this.category,
            positions = positions
        )

    fun week.on.a.plate.core.data.week.SelectionView.viewToRoom(dayId: Long): SelectionRoom =
        SelectionRoom(
            dayId = dayId,
            category = this.category
        )
}
