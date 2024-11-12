package week.on.a.plate.screens.recipeTimeline.event

import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.RecipeStepView

sealed class RecipeTimelineEvent : Event() {
    data object Done : RecipeTimelineEvent()
    data object Back : RecipeTimelineEvent()
    data class SelectStep(val ind:Int) : RecipeTimelineEvent()
    data object SetDuration : RecipeTimelineEvent()
    data object SetStart : RecipeTimelineEvent()
    data object Auto : RecipeTimelineEvent()
    data object StartWithN : RecipeTimelineEvent()
    data object AfterN : RecipeTimelineEvent()
    data object AfterLast : RecipeTimelineEvent()
    data object AfterLasts : RecipeTimelineEvent()
    data object ToEnd : RecipeTimelineEvent()
}