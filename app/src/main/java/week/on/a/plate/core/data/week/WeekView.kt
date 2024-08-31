package week.on.a.plate.core.data.week

data class WeekView(
    val id:Long,
    val selection: SelectionView,
    val days: MutableList<DayView>
)