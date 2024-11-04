package week.on.a.plate.screens.recipeTimeline.state

import androidx.compose.runtime.MutableState
import week.on.a.plate.data.dataView.recipe.RecipeStepView

data class  StepTimelineData(
    val step: RecipeStepView,
    val start: MutableState<Int>,
    val end: MutableState<Int>,
    val duration: MutableState<Int>,
    val rule: StepTimelineMode
)