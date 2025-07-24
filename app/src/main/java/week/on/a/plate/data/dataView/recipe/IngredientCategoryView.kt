package week.on.a.plate.data.dataView.recipe

import kotlinx.serialization.Serializable

@Serializable
data class IngredientCategoryView(
    val id: Long = 0,
    val name: String,
    val ingredientViews: List<IngredientView>,
)
