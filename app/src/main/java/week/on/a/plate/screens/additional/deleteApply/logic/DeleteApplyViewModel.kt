package week.on.a.plate.screens.additional.deleteApply.logic

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.additional.deleteApply.state.DeleteApplyUIState
import javax.inject.Inject

@HiltViewModel
class DeleteApplyViewModel @Inject constructor(
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = DeleteApplyUIState()
    private var resultFlow: MutableStateFlow<DeleteApplyEvent?> = MutableStateFlow<DeleteApplyEvent?>(null)

    fun onEvent(event: DeleteApplyEvent) {
        when (event) {
            DeleteApplyEvent.Apply -> done()
            DeleteApplyEvent.Cancel -> close()
        }
    }

    fun done() {
        close()
        resultFlow.value = DeleteApplyEvent.Apply
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.NavigateBack)
    }

    suspend fun launchAndGet(context: Context, title:String?=null, message: String, use: suspend (DeleteApplyEvent) -> Unit) {
        if (title!=null) state.title.value = title else state.title.value =
            context.getString(R.string.delete_apply)
        state.message.value = message
        resultFlow.value = null
        resultFlow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }
}