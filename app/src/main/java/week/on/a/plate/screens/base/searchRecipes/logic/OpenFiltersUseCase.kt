package week.on.a.plate.screens.base.searchRecipes.logic

import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import week.on.a.plate.screens.additional.filters.state.FilterResult
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

class OpenFiltersUseCase @Inject constructor() {
    operator fun invoke(
        onEvent: (MainEvent) -> Unit,
        state: SearchUIState
    ) {
        onEvent(
            MainEvent.Navigate(
                FilterDestination(
                    FilterMode.Multiple, FilterEnum.IngredientAndTag,
                    Pair(
                        state.selectedTags.value,
                        state.selectedIngredients.value
                    ), false
                )
            )
        )
    }

    //todo use
    jyyjyjy
    suspend fun afterResult(
        state: SearchUIState, res: FilterResult, search: () -> Unit
    ) {
        state.selectedTags.value = res.tags!!
        state.selectedIngredients.value = res.ingredients!!
        search()
    }
}