package week.on.a.plate.screens.additional.filters.navigation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import week.on.a.plate.app.mainActivity.event.NavParams
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import week.on.a.plate.screens.additional.filters.state.FilterResult

@Serializable
data object FilterDestination

class FilterNavParams(
    private val mode: FilterMode, private val enum: FilterEnum,
    private val lastFilters: Pair<List<RecipeTagView>, List<IngredientView>>?,
    private val isForCategory: Boolean,
    private val use: suspend (FilterResult) -> Unit
) : NavParams {
    override fun launch(vm: MainViewModel) {
        vm.viewModelScope.launch {
            vm.filterViewModel.launchAndGet(mode, enum, lastFilters, isForCategory, use)
        }
    }
}