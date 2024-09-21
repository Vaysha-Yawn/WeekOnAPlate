package week.on.a.plate.core.dialogs.addCategory.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.addCategory.event.AddCategoryEvent
import week.on.a.plate.core.dialogs.addCategory.state.AddCategoryUIState
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel


class AddCategoryViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: AddCategoryUIState
    private lateinit var resultFlow: MutableStateFlow<String?>
    var oldName:String? = null

    fun start(): Flow<String?> {
        val flow = MutableStateFlow<String?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        resultFlow.value = state.text.value
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: AddCategoryEvent) {
        when (event) {
            AddCategoryEvent.Close -> close()
            AddCategoryEvent.Done -> done()
        }
    }

    suspend fun launchAndGet(startValue:String?, use: (String) -> Unit) {
        oldName = startValue
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}