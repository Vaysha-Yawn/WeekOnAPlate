package week.on.a.plate.data.dataView.week

import java.time.LocalDateTime

data class SelectionView(
    val id: Long,
    var name: String,
    var dateTime: LocalDateTime,
    val weekOfYear: Int,
    val isForWeek: Boolean,
    val positions: MutableList<Position>
)