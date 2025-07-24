package week.on.a.plate.screens.additional.createRecipe.logic.useCase

import androidx.compose.runtime.MutableState
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import week.on.a.plate.screens.additional.filters.state.FilterResult
import javax.inject.Inject

class EditTagsUseCase @Inject constructor() {
    operator fun invoke(tags: MutableState<List<RecipeTagView>>, onEvent: (MainEvent) -> Unit) {
        onEvent(
            MainEvent.Navigate(
                FilterDestination(
                    FilterMode.Multiple, FilterEnum.Tag,
                    Pair(tags.value, listOf()), false
                )
            )
        )
    }

    //todo use
    jyyjyjy
    fun afterResult(filters: FilterResult, tags: MutableState<List<RecipeTagView>>) {
        if (filters.tags != null) {
            tags.value = filters.tags
        }
    }
}