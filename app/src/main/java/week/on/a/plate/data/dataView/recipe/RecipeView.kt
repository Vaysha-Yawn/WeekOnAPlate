package week.on.a.plate.data.dataView.recipe

import kotlinx.serialization.Serializable
import week.on.a.plate.core.navigation.LocalDateTimeSerializer
import week.on.a.plate.core.navigation.LocalTimeSerializer
import java.time.LocalDateTime
import java.time.LocalTime

@Serializable
data class RecipeView(
    val id: Long,
    var name: String,
    var description: String,
    var img: String,
    var tags: List<RecipeTagView>,
    var standardPortionsCount: Int,
    var ingredients: List<IngredientInRecipeView>,
    var steps: List<RecipeStepView>,
    var link: String,
    val inFavorite: Boolean,
    @Serializable(with = LocalDateTimeSerializer::class)
    val lastEdit: LocalDateTime,
    @Serializable(with = LocalTimeSerializer::class)
    val duration: LocalTime,
)