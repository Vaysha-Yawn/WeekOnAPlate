package week.on.a.plate.data.dataView.week

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


data class WeekView(
    val weekOfYear: Int,
    val selectionView: SelectionView,
    val days: List<DayView>,
)

fun getTitleWeek(start:LocalDate, end:LocalDate): String {
    val formatterMonth = DateTimeFormatter.ofPattern("MM")
    val formatterDay = DateTimeFormatter.ofPattern("d")

    val month = start.format(formatterMonth).capitalize(Locale("ru"))
    val startDay = start.format(formatterDay)
    val endDay = end.format(formatterDay)
    val endMonth = end.format(formatterMonth).capitalize(Locale("ru"))

    return "$startDay.$month - $endDay.$endMonth"
}