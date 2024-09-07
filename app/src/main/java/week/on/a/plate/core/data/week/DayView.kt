package week.on.a.plate.core.data.week

import java.time.LocalDate

data class DayView(
    val id: Long,
    val date: LocalDate,
    val dayInWeek: DayInWeekData,
    val selections: MutableList<SelectionView>
)