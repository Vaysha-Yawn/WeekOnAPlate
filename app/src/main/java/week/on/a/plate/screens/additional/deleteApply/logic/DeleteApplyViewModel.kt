package week.on.a.plate.screens.additional.deleteApply.logic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.additional.deleteApply.state.DeleteApplyUIState
import javax.inject.Inject

const val deleteApplyResultTag = "deleteApply"

@HiltViewModel
class DeleteApplyViewModel @Inject constructor(
) : ViewModel() {

    val state = DeleteApplyUIState()

    val dialogOpenParams = mutableStateOf<DialogOpenParams?>(null)
    val mainEvent = mutableStateOf<MainEvent?>(null)

    fun onEvent(event: DeleteApplyEvent) {
        when (event) {
            DeleteApplyEvent.Apply -> done()
            DeleteApplyEvent.Cancel -> close()
        }
    }

    private fun done() {
        mainEvent.value = MainEvent.NavigateBackWithResult(deleteApplyResultTag, true)
    }

    private fun close() {
        mainEvent.value = MainEvent.NavigateBack
    }

    fun launch(args: DeleteApplyDestination) {
        state.title.value = args.title
        state.message.value = args.message
    }
}