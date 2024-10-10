package week.on.a.plate.data.dataView.recipe

import java.time.LocalDate
import java.time.LocalTime

data class RecipeView(
    val id: Long,
    var name: String,
    var description: String,
    var img:String,
    var tags: List<RecipeTagView>,
    var prepTime: Int,
    var allTime: Int,
    var standardPortionsCount: Int,
    var ingredients: List<IngredientInRecipeView>,
    var steps: List<RecipeStepView>,
    var link: String,
    val inFavorite:Boolean,
    val dateCreated:LocalDate,
    val dateLastEdit:LocalDate,
    val timeLastEdit: LocalTime,
)