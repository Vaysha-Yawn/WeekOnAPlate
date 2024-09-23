package week.on.a.plate.core.dialogs.menu.editNote.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.menu.editNote.event.EditNoteEvent
import week.on.a.plate.core.dialogs.menu.editNote.state.EditNoteUIState
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.MainViewModel


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
        use: (Position.PositionNoteView) -> Unit,
    ) {
        EditNoteUIState(positiond)
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}