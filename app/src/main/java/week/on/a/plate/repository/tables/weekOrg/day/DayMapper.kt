package week.on.a.plate.repository.tables.weekOrg.day

import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.data.week.SelectionView


class DayMapper() {
    fun DayRoom.roomToView(selections: MutableList<SelectionView>): DayView =
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
