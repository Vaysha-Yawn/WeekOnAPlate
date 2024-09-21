package week.on.a.plate.core.dialogs.addTag.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.data.recipe.TagCategoryView
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.addTag.event.AddTagEvent
import week.on.a.plate.core.dialogs.addTag.state.AddTagUIState
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel


class AddTagViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: AddTagUIState
    private lateinit var resultFlow: MutableStateFlow<Pair<String, TagCategoryView>?>
    var startName: String? = null
    var startCategory: TagCategoryView? = null

    fun start(): Flow<Pair<String, TagCategoryView>?> {
        val flow = MutableStateFlow<Pair<String, TagCategoryView>?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        if (state.category.value == null) return
        val result = Pair(state.text.value, state.category.value!!)
        resultFlow.value = result
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: AddTagEvent) {
        when (event) {
            AddTagEvent.Close -> close()
            AddTagEvent.Done -> done()
            AddTagEvent.ChooseCategory -> TODO()
        }
    }

    suspend fun launchAndGet(
        oldName: String?,
        oldCategory: TagCategoryView?, use: (Pair<String, TagCategoryView>) -> Unit
    ) {
        startName = oldName
        startCategory = oldCategory
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}