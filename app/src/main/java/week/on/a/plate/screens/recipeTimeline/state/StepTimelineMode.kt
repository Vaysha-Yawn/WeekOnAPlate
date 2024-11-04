package week.on.a.plate.screens.recipeTimeline.state

import week.on.a.plate.data.dataView.recipe.RecipeStepView

sealed class StepTimelineMode(){
    data object Target : StepTimelineMode()
    data class StartWithN (val n : RecipeStepView) : StepTimelineMode()
    data class AfterN (val n : RecipeStepView): StepTimelineMode()
    data object AfterLast : StepTimelineMode()
    data object AfterLasts : StepTimelineMode()
    data object ToEnd : StepTimelineMode()
}