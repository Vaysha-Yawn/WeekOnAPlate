package week.on.a.plate.data.dataView.recipe

import kotlinx.serialization.Serializable

@Serializable
data class TagCategoryView(
    val id: Long = 0,
    val name: String,
    var tags: List<RecipeTagView>,
)
