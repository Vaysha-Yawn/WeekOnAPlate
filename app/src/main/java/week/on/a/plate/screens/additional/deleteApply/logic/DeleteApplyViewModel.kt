package week.on.a.plate.screens.additional.deleteApply.logic

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.BackNavParams
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.event.NavigateBackDest
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.additional.deleteApply.state.DeleteApplyUIState
import javax.inject.Inject

@HiltViewModel
class DeleteApplyViewModel @Inject constructor(
) : ViewModel() {

    val state = DeleteApplyUIState()
    private var resultFlow: MutableStateFlow<DeleteApplyEvent?> = MutableStateFlow<DeleteApplyEvent?>(null)

    val dialogOpenParams = mutableStateOf<DialogOpenParams?>(null)
    val mainEvent = mutableStateOf<MainEvent?>(null)

    fun onEvent(event: DeleteApplyEvent) {
        when (event) {
            DeleteApplyEvent.Apply -> done()
            DeleteApplyEvent.Cancel -> close()
        }
    }

    fun done() {
        resultFlow.value = DeleteApplyEvent.Apply
        close()
    }

    fun close() {
        mainEvent.value = MainEvent.Navigate(NavigateBackDest, BackNavParams)
    }

    suspend fun launchAndGet(
        context: Context,
        title: String? = null,
        message: String,
        use: suspend (DeleteApplyEvent) -> Unit
    ) {
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