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

    val month = start.format(formatterMonth)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    val startDay = start.format(formatterDay)
    val endDay = end.format(formatterDay)
    val endMonth = end.format(formatterMonth)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

    return "$startDay.$month - $endDay.$endMonth"
}