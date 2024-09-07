package week.on.a.plate.core.data.week

import week.on.a.plate.core.data.recipe.IngredientInRecipeView
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView

sealed class Position {
    data class PositionRecipeView(
        val id: Long,
        val recipe: RecipeShortView,
        val portionsCount: Int
    ) : Position()

    data class PositionIngredientView(
        val id: Long,
        val ingredient: IngredientInRecipeView
    ) : Position()

    data class PositionDraftView(
        val id: Long,
        val tags: List<RecipeTagView>,
        val ingredients: List<IngredientView>,
    ) : Position()

    data class PositionNoteView(
        val id: Long,
        val note: String,
    ) : Position()
}