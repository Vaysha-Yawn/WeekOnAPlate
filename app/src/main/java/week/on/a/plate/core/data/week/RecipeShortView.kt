package week.on.a.plate.core.data.week

import kotlinx.serialization.Serializable

@Serializable
data class RecipeShortView(
    val id: Long,
    var name: String
)