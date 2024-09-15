package week.on.a.plate.core.data.week

import kotlinx.serialization.Serializable
import week.on.a.plate.core.data.recipe.IngredientInRecipeView
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView

@Serializable
sealed class Position(val idPos:Long) {

    @Serializable
    data class PositionRecipeView(
        val id: Long,
        val recipe: RecipeShortView,
        val portionsCount: Int,
        var selectionId: Long,
    ) : Position(id)

    @Serializable
    data class PositionIngredientView(
        val id: Long,
        val ingredient: IngredientInRecipeView,
        var selectionId: Long,
    ) : Position(id)

    @Serializable
    data class PositionDraftView(
        val id: Long,
        val tags: List<RecipeTagView>,
        val ingredients: List<IngredientView>,
        var selectionId: Long,
    ) : Position(id)

    @Serializable
    data class PositionNoteView(
        val id: Long,
        val note: String,
        var selectionId: Long,
    ) : Position(id)
}