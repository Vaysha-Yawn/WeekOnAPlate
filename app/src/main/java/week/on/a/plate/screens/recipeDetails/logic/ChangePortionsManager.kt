package week.on.a.plate.screens.recipeDetails.logic


import week.on.a.plate.screens.recipeDetails.state.RecipeDetailsState
import javax.inject.Inject

class ChangePortionsManager @Inject constructor() {

    fun plusPortionsView(state:RecipeDetailsState, update:()->Unit) {
        state.currentPortions.intValue = state.currentPortions.intValue.plus(1)
        update()
    }

    fun minusPortionsView(state:RecipeDetailsState, update:()->Unit) {
        if (state.currentPortions.intValue > 1) {
            state.currentPortions.intValue = state.currentPortions.intValue.minus(1)
            update()
        }
    }
}