package week.on.a.plate.screens.additional.createRecipe.navigation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import week.on.a.plate.app.mainActivity.event.NavParams
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState

@Serializable
object RecipeCreateDestination

class RecipeCreateNavParams(
    private val oldRecipe: RecipeView?, private val isForCreate: Boolean,
    private val use: (RecipeCreateUIState) -> Unit
) : NavParams {
    override fun launch(vm: MainViewModel) {
        vm.viewModelScope.launch {
            vm.recipeCreateViewModel.launchAndGet(oldRecipe, isForCreate, use)
        }
    }

}