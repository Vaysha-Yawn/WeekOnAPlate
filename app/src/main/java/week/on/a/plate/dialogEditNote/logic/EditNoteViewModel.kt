package week.on.a.plate.dialogEditNote.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.dialogEditNote.event.EditNoteEvent
import week.on.a.plate.dialogEditNote.state.EditNoteUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class EditNoteViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: EditNoteUIState
    private lateinit var resultFlow: MutableStateFlow<Position.PositionNoteView?>

    fun start(): Flow<Position.PositionNoteView?> {
        val flow = MutableStateFlow<Position.PositionNoteView?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        val changedNote =
            Position.PositionNoteView(state.note?.id?:0, state.text.value, state.note?.selectionId?:0)
        resultFlow.value = changedNote
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: EditNoteEvent) {
        when (event) {
            EditNoteEvent.Close -> close()
            EditNoteEvent.Done -> done()
        }
    }

    suspend fun launchAndGet(
        positiond: Position.PositionNoteView?,
        use: suspend (Position.PositionNoteView) -> Unit,
    ) {
        state = EditNoteUIState(positiond)
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}