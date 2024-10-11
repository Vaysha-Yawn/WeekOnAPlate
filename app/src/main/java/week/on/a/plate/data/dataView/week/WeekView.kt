package week.on.a.plate.data.dataView.week

import java.time.format.DateTimeFormatter
import java.util.Locale


data class WeekView(
    val weekOfYear: Int,
    val selectionView: SelectionView,
    val days: List<DayView>,
)

fun WeekView.getTitle(): String {
    val weekView = this
    val formatterMonth = DateTimeFormatter.ofPattern("MMMM")
    val formatterDay = DateTimeFormatter.ofPattern("d")

    val start = weekView.days[0].date
    val end = weekView.days[6].date

    val month = start.format(formatterMonth).capitalize(Locale("ru"))
    val startDay = start.format(formatterDay)
    val endDay = end.format(formatterDay)
    val endMonth = end.format(formatterMonth).capitalize(Locale("ru"))

    return "$startDay $month - $endDay $endMonth"
}