package week.on.a.plate.core.data.week

import week.on.a.plate.core.data.recipe.IngredientInRecipeView
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView

sealed class Position(val idPos:Long) {
    data class PositionRecipeView(
        val id: Long,
        val recipe: RecipeShortView,
        val portionsCount: Int,
        var selectionId: Long,
    ) : Position(id)

    data class PositionIngredientView(
        val id: Long,
        val ingredient: IngredientInRecipeView,
        var selectionId: Long,
    ) : Position(id)

    data class PositionDraftView(
        val id: Long,
        val tags: List<RecipeTagView>,
        val ingredients: List<IngredientView>,
        var selectionId: Long,
    ) : Position(id)

    data class PositionNoteView(
        val id: Long,
        val note: String,
        var selectionId: Long,
    ) : Position(id)
}