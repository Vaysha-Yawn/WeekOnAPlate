package week.on.a.plate.screens.base.searchRecipes.logic

import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.dialogs.forSearchScreen.filtersMore.logic.FiltersMoreViewModel
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

class FiltersMoreUseCase @Inject constructor() {
    operator fun invoke(
        mainViewModel: MainViewModel, state: SearchUIState, search: () -> Unit
    ) {
        FiltersMoreViewModel.launch(
            state.favoriteChecked.value,
            state.allTime.intValue,
            mainViewModel
        ) { stated ->
            state.allTime.intValue = stated.allTime.intValue
            state.favoriteChecked.value = stated.favoriteIsChecked.value
            search()
        }
    }
}