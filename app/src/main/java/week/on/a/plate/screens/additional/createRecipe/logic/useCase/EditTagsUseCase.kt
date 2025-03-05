package week.on.a.plate.screens.additional.createRecipe.logic.useCase

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.screens.additional.filters.navigation.FilterDestination
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import javax.inject.Inject

class EditTagsUseCase @Inject constructor() {
    operator fun invoke(mainViewModel: MainViewModel, tags: MutableState<List<RecipeTagView>>) {
        mainViewModel.viewModelScope.launch {
            mainViewModel.nav.navigate(FilterDestination)
            mainViewModel.filterViewModel.launchAndGet(
                FilterMode.Multiple, FilterEnum.Tag,
                Pair(tags.value, listOf()), false
            ) {
                if (it.tags != null) {
                    tags.value = it.tags
                }
            }
        }
    }
}