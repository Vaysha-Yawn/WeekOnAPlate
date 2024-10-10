package week.on.a.plate.data.dataView.week

import java.time.LocalDate
import java.time.LocalTime

data class SelectionView(
    val id:Long,
    var name:String,
    val date:LocalDate,
    val weekOfYear:Int,
    val isForWeek:Boolean,
    val time: LocalTime,
    val positions: MutableList<Position>
)