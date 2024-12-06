package week.on.a.plate.data.dataView.recipe

import java.time.LocalDateTime
import java.time.LocalTime

data class RecipeView(
    val id: Long,
    var name: String,
    var description: String,
    var img:String,
    var tags: List<RecipeTagView>,
    var standardPortionsCount: Int,
    var ingredients: List<IngredientInRecipeView>,
    var steps: List<RecipeStepView>,
    var link: String,
    val inFavorite:Boolean,
    val lastEdit:LocalDateTime,

    //added
    val duration:LocalTime,
)