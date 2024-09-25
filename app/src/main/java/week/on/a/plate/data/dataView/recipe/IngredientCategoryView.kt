package week.on.a.plate.data.dataView.recipe


data class IngredientCategoryView(
    val id: Long = 0,
    val name: String,
    val ingredientViews: List<IngredientView>,
)
