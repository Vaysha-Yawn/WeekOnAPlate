package week.on.a.plate.screens.createRecipe.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.screens.createRecipe.state.RecipeStepState

sealed class RecipeCreateEvent:Event() {
    data class EditIngredient(val ingredient: IngredientInRecipeView) : RecipeCreateEvent()
    data class DeleteStep(val recipeStepState: RecipeStepState) : RecipeCreateEvent()
    data class ClearTimer(val recipeStepState: RecipeStepState) : RecipeCreateEvent()
    data class DeleteImage(val recipeStepState: RecipeStepState) : RecipeCreateEvent()
    data class EditImage(val recipeStepState: RecipeStepState, val context:Context) : RecipeCreateEvent()
    data class EditTimer(val context:Context, val recipeStepState: RecipeStepState) : RecipeCreateEvent()
    data class DeleteIngredient(val ingredient: IngredientInRecipeView) : RecipeCreateEvent()
    data class EditPinnedIngredients(val recipeStepState: RecipeStepState) : RecipeCreateEvent()
    data class EditMainImage(val context:Context) : RecipeCreateEvent()
    data class EditRecipeDuration(val context:Context) : RecipeCreateEvent()
    data object Done: RecipeCreateEvent()
    data object Close: RecipeCreateEvent()
    data object EditTags : RecipeCreateEvent()
    data object AddIngredient : RecipeCreateEvent()
    data object AddStep : RecipeCreateEvent()
    data object AddManyIngredients : RecipeCreateEvent()
    data object OpenDialogExitApplyFromCreateRecipe : RecipeCreateEvent()
}