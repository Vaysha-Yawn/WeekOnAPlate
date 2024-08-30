package week.on.a.plate.repository.tables.weekOrg.week

import week.on.a.plate.core.data.week.DayData
import week.on.a.plate.core.data.week.RecipeInMenu
import week.on.a.plate.core.data.week.SelectionInDayData


class WeekMapper() {
    fun WeekData.roomToView( selection: SelectionInDayData, days: MutableList<DayData>): week.on.a.plate.core.data.week.WeekData =
        week.on.a.plate.core.data.week.WeekData(
            id = this.weekId,
            selection = selection,
            days = days
        )

    fun week.on.a.plate.core.data.week.WeekData.viewToRoom(): WeekData =
        WeekData(
            weekId = this.id,
            selectionId = selection.id
        )
}
