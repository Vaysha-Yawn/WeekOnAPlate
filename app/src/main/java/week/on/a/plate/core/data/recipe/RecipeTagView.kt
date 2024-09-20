package week.on.a.plate.core.data.recipe

import kotlinx.serialization.Serializable


@Serializable
data class RecipeTagView(
    val id: Long = 0,
    var tagName: String,
)
