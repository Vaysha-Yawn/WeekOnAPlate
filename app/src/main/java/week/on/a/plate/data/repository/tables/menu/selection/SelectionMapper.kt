package week.on.a.plate.data.repository.tables.menu.selection

import androidx.compose.runtime.State
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

    fun SelectionRoom.roomToViewState(
        positions: MutableList<State<Position>>,
    ): SelectionView =
        SelectionView(
            id = id,
            name = name,
            dateTime = dateTime,
            weekOfYear = weekOfYear,
            isForWeek = isForWeek,
            positions = positions.map { it.value }.toMutableList(),
        )

    fun SelectionView.viewToRoom(): SelectionRoom =
        SelectionRoom(
            name = name,
            dateTime = dateTime,
            weekOfYear = weekOfYear,
            isForWeek = isForWeek,
        )
}
