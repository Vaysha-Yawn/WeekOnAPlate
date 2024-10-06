package week.on.a.plate.data.dataView.week

import kotlinx.serialization.Serializable

@Serializable
data class RecipeShortView(
    val id: Long,
    var name: String,
    var image:String,
)