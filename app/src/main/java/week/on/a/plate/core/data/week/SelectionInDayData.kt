package week.on.a.plate.core.data.week

data class SelectionInDayData(
    val id:Long,
    val category:String,
    val recipes: MutableList<RecipeInMenu>
)