package week.on.a.plate.data.dataView.recipe

import java.time.LocalTime

data class RecipeStepView(
    val id:Long,
    val description: String,
    val image:String,
    val timer:Long,
    val duration: LocalTime,
)