package week.on.a.plate.data.dataView.recipe

import kotlinx.serialization.Serializable

@Serializable
data class RecipeStepView(
    val id: Long,
    val description: String,
    val image: String,
    val timer: Long,
    val ingredientsPinnedId:List<Long>
)