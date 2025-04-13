package week.on.a.plate.screens.base.searchRecipes.logic

import androidx.compose.runtime.MutableState
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.dialogs.forSearchScreen.filtersMore.logic.FiltersMoreViewModel
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

class FiltersMoreUseCase @Inject constructor() {
    operator fun invoke(
        dialogOpenParams: MutableState<DialogOpenParams?>, state: SearchUIState, search: () -> Unit
    ) {
        val params = FiltersMoreViewModel.FiltersMoreDialogParams(
            state.favoriteChecked.value,
            state.allTime.intValue
        ) { stated ->
            state.allTime.intValue = stated.allTime.intValue
            state.favoriteChecked.value = stated.favoriteIsChecked.value
            search()
        }
        dialogOpenParams.value = params
    }
}