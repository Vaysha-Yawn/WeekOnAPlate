package week.on.a.plate.data.dataView.recipe

data class RecipeStepView(
    val id:Long,
    val title:String,
    val description: String,
    val image:String,
    val timer:Long //milli sec
)