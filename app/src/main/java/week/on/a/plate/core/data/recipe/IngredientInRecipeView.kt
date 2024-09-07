package week.on.a.plate.core.data.recipe

data class IngredientInRecipeView(
    val id: Long,
    val ingredientView: IngredientView,
    val description:String,
    val count:Double
)