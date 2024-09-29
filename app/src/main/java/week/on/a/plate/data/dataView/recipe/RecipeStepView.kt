package week.on.a.plate.data.dataView.recipe

data class RecipeStepView(
    val id:Long,
    val description: String,
    val image:String,
    val timer:Long //milli sec
)