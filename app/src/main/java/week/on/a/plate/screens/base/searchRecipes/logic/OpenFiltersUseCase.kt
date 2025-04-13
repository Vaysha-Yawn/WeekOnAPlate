package week.on.a.plate.screens.base.searchRecipes.logic

import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.navigation.FilterNavParams
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

class OpenFiltersUseCase @Inject constructor() {
    operator fun invoke(
        onEvent: (Event) -> Unit,
        state: SearchUIState,
        search: () -> Unit
    ) {
        val params = FilterNavParams(
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
        onEvent(MainEvent.Navigate(FilterDestination, params))
    }
}