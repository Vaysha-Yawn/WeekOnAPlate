package week.on.a.plate.data.repository.tables.menu.position.note


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import week.on.a.plate.data.dataView.week.Position
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PositionNoteRepository @Inject constructor(
    private val positionNoteDAO: PositionNoteDAO
) {

    suspend fun getAllInSel(selectionId: Long): List<Position.PositionNoteView> {
        return positionNoteDAO.getAllInSel(selectionId).map { noteRoom ->
            noteRoom.noteRoomToView()
        }
    }

    fun getAllInSelFlow(
        selectionId: Long
    ): Flow<List<Position>> {
        return positionNoteDAO.getAllInSelFlow(selectionId).onStart { emit(listOf()) }.map {
            it.map { noteRoom ->
                noteRoom.noteRoomToView()
            }
        }
    }

    private fun PositionNoteRoom.noteRoomToView(): Position.PositionNoteView {
        return with(PositionNoteMapper()) {
            roomToView()
        }
    }

    suspend fun insert(note: Position.PositionNoteView, selectionId: Long): Long {
        val positionRoom = with(PositionNoteMapper()) { note.viewToRoom(selectionId) }
        return positionNoteDAO.insert(positionRoom)
    }

    suspend fun update(id: Long, description: String, selectionId: Long) {
        positionNoteDAO.update(
            PositionNoteRoom(description, selectionId).apply {
                this.id = id
            }
        )
    }

    suspend fun delete(noteId: Long) {
        positionNoteDAO.deleteById(noteId)
    }


}
