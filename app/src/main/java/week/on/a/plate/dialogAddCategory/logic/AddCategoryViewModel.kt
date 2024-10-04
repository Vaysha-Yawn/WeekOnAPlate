package week.on.a.plate.dialogAddCategory.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.dialogAddCategory.event.AddCategoryEvent
import week.on.a.plate.dialogAddCategory.state.AddCategoryUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class AddCategoryViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state =  AddCategoryUIState()
    private lateinit var resultFlow: MutableStateFlow<String?>

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

    suspend fun launchAndGet(startValue:String?, use: suspend (String) -> Unit) {
        state.text.value = startValue?:""
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}