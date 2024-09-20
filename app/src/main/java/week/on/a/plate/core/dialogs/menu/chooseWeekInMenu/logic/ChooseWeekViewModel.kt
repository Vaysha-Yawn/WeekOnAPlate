package week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.logic

import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.state.ChooseWeekUIState
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import week.on.a.plate.core.tools.dateToLocalDate
import java.time.LocalDate


class ChooseWeekViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: ChooseWeekUIState
    private lateinit var resultFlow: MutableStateFlow<LocalDate?>

    @OptIn(ExperimentalMaterial3Api::class)
    fun getLocalDate(state: ChooseWeekUIState): LocalDate? {
        return state.state.selectedDateMillis?.dateToLocalDate()
    }

    fun start(): Flow<LocalDate?> {
        val flow = MutableStateFlow<LocalDate?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        val date = getLocalDate(state)
        resultFlow.value = date
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: ChooseWeekDialogEvent) {
        when (event) {
            ChooseWeekDialogEvent.Close -> close()
            ChooseWeekDialogEvent.Done -> done()
        }
    }

    suspend fun launchAndGet(use: (LocalDate) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}