package week.on.a.plate.core.data.week

import week.on.a.plate.core.data.recipe.RecipeStateView

data class RecipeInMenuView(
    val id: Long,
    var state: RecipeStateView,
    var recipe: RecipeShortView,
    var portionsCount: Int
)