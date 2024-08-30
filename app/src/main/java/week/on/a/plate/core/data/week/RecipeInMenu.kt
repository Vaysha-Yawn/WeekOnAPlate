package week.on.a.plate.core.data.week

import week.on.a.plate.core.data.recipe.RecipeState
import week.on.a.plate.core.data.recipe.RecipeView

data class RecipeInMenu(
    val id:Long,
    var state: RecipeState,
    var recipe: RecipeView,
    var portionsCount:Int
)