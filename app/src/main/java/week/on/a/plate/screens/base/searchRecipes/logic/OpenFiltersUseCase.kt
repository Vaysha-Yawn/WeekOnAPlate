package week.on.a.plate.screens.base.searchRecipes.logic

import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

class OpenFiltersUseCase @Inject constructor() {
    suspend operator fun invoke(
        mainViewModel: MainViewModel,
        state: SearchUIState,
        search: () -> Unit
    ) {
        val vm = mainViewModel.filterViewModel
        mainViewModel.nav.navigate(FilterDestination)
        vm.launchAndGet(
            FilterMode.Multiple, FilterEnum.IngredientAndTag,
            Pair(
                state.selectedTags.value,
                state.selectedIngredients.value
            ), false
        ) { resultFilters ->
            state.selectedTags.value = resultFilters.tags!!
            state.selectedIngredients.value = resultFilters.ingredients!!
            search()
        }
    }
}