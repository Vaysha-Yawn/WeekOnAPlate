package week.on.a.plate.screens.additional.recipeDetails.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.app.mainActivity.event.NavParams
import week.on.a.plate.app.mainActivity.logic.MainViewModel

@Serializable
data object RecipeDetailsDestination

class RecipeDetailsNavParams(private val resId: Long, private val portionsCount: Int?) : NavParams {
    override fun launch(vm: MainViewModel) {
        vm.recipeDetailsViewModel.launch(resId, portionsCount)
    }
}