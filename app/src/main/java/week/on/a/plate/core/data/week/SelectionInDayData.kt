package week.on.a.plate.core.data.week

data class SelectionInDayData(
    val category:String,
    val recipes: MutableList<RecipeInMenu>
)