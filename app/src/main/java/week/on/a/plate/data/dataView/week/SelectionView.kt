package week.on.a.plate.data.dataView.week

data class SelectionView(
    val id:Long,
    val category:String,
    val positions: MutableList<Position>
)