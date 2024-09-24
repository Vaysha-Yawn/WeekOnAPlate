package week.on.a.plate.recipeFullScreen.event

import week.on.a.plate.core.Event

sealed class RecipeDetailsEvent:Event() {
    data object Edit:RecipeDetailsEvent()
    data object Back:RecipeDetailsEvent()
    data object AddToCart:RecipeDetailsEvent()
    data object SwitchFavorite:RecipeDetailsEvent()
    data object AddToMenu:RecipeDetailsEvent()
    data class ChangeTab(val selectedTabIndex: Int) : RecipeDetailsEvent()
    data object PlusPortionsView:RecipeDetailsEvent()
    data object MinusPortionsView:RecipeDetailsEvent()
    data class StartTimerForStep(val stepIndex: Int) : RecipeDetailsEvent()
}