package week.on.a.plate.data.dataView.week

import java.time.LocalDate

data class DayView(
    val id: Long,
    val date: LocalDate,
    val dayInWeek: DayInWeekData,
    val selections: MutableList<SelectionView>
)