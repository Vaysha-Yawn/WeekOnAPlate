package week.on.a.plate.core.data.week

data class WeekView(
    val id:Long,
    val selection: SelectionView,
    var days: MutableList<DayView>
)