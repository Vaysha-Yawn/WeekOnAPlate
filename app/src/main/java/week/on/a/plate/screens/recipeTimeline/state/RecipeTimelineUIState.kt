package week.on.a.plate.screens.recipeTimeline.state

import androidx.compose.runtime.MutableState
import week.on.a.plate.data.dataView.recipe.RecipeStepView

data class RecipeTimelineUIState(
    val activeStep: MutableState<RecipeStepView>,
    val allSteps : List<RecipeStepView>,
    val allUISteps : MutableState<List<RecipeStepView>>,
    val plannedAllTime : MutableState<Int>,
    val realAllTime : MutableState<Int>,
)