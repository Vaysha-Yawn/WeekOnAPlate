package week.on.a.plate.screens.additional.recipeDetails.logic.utils


import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import javax.inject.Inject

class ChangePortionsManager @Inject constructor() {

    fun plusPortionsView(state: RecipeDetailsState, updatePortions: () -> Unit) {
        state.currentPortions.intValue = state.currentPortions.intValue.plus(1)
        updatePortions()
    }

    fun minusPortionsView(state: RecipeDetailsState, updatePortions: () -> Unit) {
        if (state.currentPortions.intValue > 1) {
            state.currentPortions.intValue = state.currentPortions.intValue.minus(1)
            updatePortions()
        }
    }
}