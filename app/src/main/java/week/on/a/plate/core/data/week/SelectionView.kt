package week.on.a.plate.core.data.week

data class SelectionView(
    val id:Long,
    val category:String,
    val positions: MutableList<Position>
)