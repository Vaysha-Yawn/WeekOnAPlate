package week.on.a.plate.data.dataView.recipe

import kotlinx.serialization.Serializable


@Serializable
data class RecipeTagView(
    val id: Long = 0,
    var tagName: String,
)
