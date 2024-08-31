package week.on.a.plate.repository.tables.weekOrg.week

import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.data.week.SelectionView


class WeekMapper() {
    fun WeekRoom.roomToView(selection: SelectionView, days: MutableList<DayView>): week.on.a.plate.core.data.week.WeekView =
        week.on.a.plate.core.data.week.WeekView(
            id = this.weekId,
            selection = selection,
            days = days
        )

    fun week.on.a.plate.core.data.week.WeekView.viewToRoom(selectionId:Long): WeekRoom =
        WeekRoom(
            selectionId = selectionId
        )
}
