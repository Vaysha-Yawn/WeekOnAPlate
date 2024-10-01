package week.on.a.plate.data.dataView.week

import java.time.LocalDate

data class SelectionView(
    val id:Long,
    val name:String,
    val date:LocalDate,
    val weekOfYear:Int,
    val isForWeek:Boolean,
    val positions: MutableList<Position>
)