package week.on.a.plate.screens.createRecipe.event

import androidx.compose.runtime.MutableState
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.screens.createRecipe.state.RecipeStepState

sealed class RecipeCreateEvent:Event() {
    class EditIngredient(val ingredient: IngredientInRecipeView) : RecipeCreateEvent()
    class DeleteStep(val recipeStepState: RecipeStepState) : RecipeCreateEvent()
    class ClearTime(val recipeStepState: RecipeStepState) : RecipeCreateEvent()
    class DeleteImage(val recipeStepState: RecipeStepState) : RecipeCreateEvent()
    class EditImage(val recipeStepState: RecipeStepState) : RecipeCreateEvent()
    class EditTimer(val recipeStepState: RecipeStepState) : RecipeCreateEvent()
    class DeleteIngredient(val ingredient: IngredientInRecipeView) : RecipeCreateEvent()
    class EditPinnedIngredients(val recipeStepState: RecipeStepState) :
        RecipeCreateEvent()

    data object EditMainImage : RecipeCreateEvent()
    data object Done: RecipeCreateEvent()
    data object Close: RecipeCreateEvent()
    data object EditTags : RecipeCreateEvent()
    data object AddIngredient : RecipeCreateEvent()
    data object AddStep : RecipeCreateEvent()
    data object AddManyIngredients : RecipeCreateEvent()
    data object SetTimeline : RecipeCreateEvent()
}