package week.on.a.plate.data.dataView.recipe

import kotlinx.serialization.Serializable

@Serializable
data class IngredientView(
    val ingredientId: Long,
    val img: String,
    val name: String,
    val measure:String
)