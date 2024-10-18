package week.on.a.plate.data.repository.tables.menu.selection

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView


class SelectionMapper() {
    fun SelectionRoom.roomToView(
        positions: MutableList<Position>,
    ): SelectionView =
        SelectionView(
            id = id,
            name = name,
            dateTime = dateTime,
            weekOfYear = weekOfYear,
            isForWeek = isForWeek,
            positions = positions,
        )

    fun SelectionView.viewToRoom(): SelectionRoom =
        SelectionRoom(
            name = name,
            dateTime = dateTime,
            weekOfYear = weekOfYear,
            isForWeek = isForWeek,
        )
}
