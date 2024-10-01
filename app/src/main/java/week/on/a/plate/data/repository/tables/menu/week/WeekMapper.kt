package week.on.a.plate.data.repository.tables.menu.week

import week.on.a.plate.data.dataView.week.DayView
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.WeekView


class WeekMapper() {
    fun WeekRoom.roomToView(selection: SelectionView, days: List<DayView>): WeekView =
        WeekView(
            id = this.weekId,
            selection = selection,
            days = days
        )

    fun WeekView.viewToRoom(selectionId:Long): WeekRoom =
        WeekRoom(
            selectionId = selectionId
        )
}
