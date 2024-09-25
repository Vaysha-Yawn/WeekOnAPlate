package week.on.a.plate.data.dataView.week

data class WeekView(
    val id:Long,
    val selection: SelectionView,
    var days: MutableList<DayView>
)