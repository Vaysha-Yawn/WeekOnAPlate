package week.on.a.plate.screens.additional.createRecipe.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.data.dataView.recipe.RecipeView

@Serializable
data class RecipeCreateDestination(
    val oldRecipeId: Long?,
    val isForCreate: Boolean,
    val recipeStart: RecipeView?
)