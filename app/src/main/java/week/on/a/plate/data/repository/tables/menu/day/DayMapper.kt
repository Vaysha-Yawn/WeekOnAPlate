package week.on.a.plate.data.repository.tables.menu.day

import week.on.a.plate.data.dataView.week.DayView
import week.on.a.plate.data.dataView.week.SelectionView


class DayMapper() {
    fun DayRoom.roomToView(selections: List<SelectionView>): DayView =
        DayView(
            id = this.dayId,
            date = this.date,
            dayInWeek = this.dayInWeek,
            selections = selections
        )

    fun DayView.viewToRoom(weekId:Long): DayRoom =
        DayRoom(
            date = this.date,
            dayInWeek = this.dayInWeek,
            weekId = weekId
        )
}
