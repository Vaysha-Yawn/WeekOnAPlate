package week.on.a.plate.data.dataView.recipe

import kotlinx.serialization.Serializable

@Serializable
data class IngredientInRecipeView(
    val id: Long,
    val ingredientView: IngredientView,
    var description:String,
    var count:Int
)