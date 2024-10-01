package week.on.a.plate.data.dataView.week


data class WeekView(
    val weekOfYear: Int,
    val selectionView: SelectionView,
    val days: List<DayView>,
)