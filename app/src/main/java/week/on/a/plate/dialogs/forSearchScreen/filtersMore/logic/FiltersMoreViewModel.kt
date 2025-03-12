package week.on.a.plate.dialogs.forSearchScreen.filtersMore.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.dialogs.forSearchScreen.filtersMore.event.FiltersMoreEvent
import week.on.a.plate.dialogs.forSearchScreen.filtersMore.state.FiltersMoreUIState


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