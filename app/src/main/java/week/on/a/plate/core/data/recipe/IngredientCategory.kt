package week.on.a.plate.core.data.recipe


data class IngredientCategory(
    val id: Long = 0,
    val name: String,
    val ingredients: List<Ingredient>,
)
