package week.on.a.plate.screens.additional.createRecipe.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.screens.additional.createRecipe.state.RecipeStepState

sealed interface RecipeCreateEvent : Event {
    class EditIngredient(val ingredient: IngredientInRecipeView) : RecipeCreateEvent
    class DeleteStep(val recipeStepState: RecipeStepState) : RecipeCreateEvent
    class ClearTimer(val recipeStepState: RecipeStepState) : RecipeCreateEvent
    class DeleteImage(val recipeStepState: RecipeStepState) : RecipeCreateEvent
    class EditImage(val recipeStepState: RecipeStepState, val context: Context) : RecipeCreateEvent
    class EditTimer(val context: Context, val recipeStepState: RecipeStepState) : RecipeCreateEvent
    class DeleteIngredient(val ingredient: IngredientInRecipeView) : RecipeCreateEvent
    class EditPinnedIngredients(val recipeStepState: RecipeStepState) : RecipeCreateEvent
    class EditMainImage(val context: Context) : RecipeCreateEvent
    class EditRecipeDuration(val context: Context) : RecipeCreateEvent
    object Done : RecipeCreateEvent
    object Close : RecipeCreateEvent
    object EditTags : RecipeCreateEvent
    object AddIngredient : RecipeCreateEvent
    object AddStep : RecipeCreateEvent
    object AddManyIngredients : RecipeCreateEvent
    object OpenDialogExitApplyFromCreateRecipe : RecipeCreateEvent
}