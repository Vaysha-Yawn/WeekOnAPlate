package week.on.a.plate.dialogs.filtersMore.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.filtersMore.event.FiltersMoreEvent
import week.on.a.plate.dialogs.filtersMore.state.FiltersMoreUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class FiltersMoreViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state: FiltersMoreUIState = FiltersMoreUIState()
    private lateinit var resultFlow: MutableStateFlow<FiltersMoreUIState?>

    fun start(): Flow<FiltersMoreUIState?> {
        val flow = MutableStateFlow<FiltersMoreUIState?>(null)
        resultFlow = flow
        return flow
    }

    fun close() {
        resultFlow.value = state
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: FiltersMoreEvent) {
        when (event) {
            FiltersMoreEvent.Close -> close()
        }
    }

    suspend fun launchAndGet(favoriteChecked:Boolean, allTime:Int, use: (FiltersMoreUIState) -> Unit) {
        state.favoriteIsChecked.value = favoriteChecked
        state.allTime.intValue = allTime
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}