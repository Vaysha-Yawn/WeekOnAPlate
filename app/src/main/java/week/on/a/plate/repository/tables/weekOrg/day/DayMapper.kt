package week.on.a.plate.repository.tables.weekOrg.day

import week.on.a.plate.core.data.week.SelectionInDayData


class DayMapper() {
    fun DayData.roomToView(selections: MutableList<SelectionInDayData>): week.on.a.plate.core.data.week.DayData =
        week.on.a.plate.core.data.week.DayData(
            id = this.dayId,
            date = this.date,
            dayInWeek = this.dayInWeek,
            selections = selections
        )

    fun week.on.a.plate.core.data.week.DayData.viewToRoom(weekId:Long): DayData =
        DayData(
            dayId = this.id,
            date = this.date,
            dayInWeek = this.dayInWeek,
            weekId = weekId
        )
}
