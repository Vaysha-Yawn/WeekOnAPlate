package week.on.a.plate.dialogs.filtersMore.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.filtersMore.event.FiltersMoreEvent
import week.on.a.plate.dialogs.filtersMore.state.FiltersMoreUIState
import week.on.a.plate.mainActivity.logic.MainViewModel


class FiltersMoreViewModel(
    favoriteChecked: Boolean, allTime: Int,
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (FiltersMoreUIState) -> Unit
) : DialogViewModel<FiltersMoreUIState>(
    scope,
    openDialog,
    closeDialog,
    use
) {
    val state: FiltersMoreUIState = FiltersMoreUIState()

    init {
        state.favoriteIsChecked.value = favoriteChecked
        state.allTime.intValue = allTime
    }

    fun onEvent(event: FiltersMoreEvent) {
        when (event) {
            FiltersMoreEvent.Close -> done(state)
        }
    }

    companion object {
        fun launch(
            favoriteChecked: Boolean, allTime: Int,
            mainViewModel: MainViewModel, useResult: (FiltersMoreUIState) -> Unit
        ) {
            FiltersMoreViewModel(
                favoriteChecked, allTime,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }

}