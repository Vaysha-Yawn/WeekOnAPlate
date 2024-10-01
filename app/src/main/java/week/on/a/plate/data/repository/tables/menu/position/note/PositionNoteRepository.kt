package week.on.a.plate.data.repository.tables.menu.position.note


import week.on.a.plate.data.dataView.week.Position
import javax.inject.Inject


class PositionNoteRepository @Inject constructor(
    private val positionNoteDAO: PositionNoteDAO
) {

    suspend fun getAllInSel(selectionId: Long): List<Position.PositionNoteView> {
        val positionNoteRoom = positionNoteDAO.getAllInSel(selectionId)
        val list = mutableListOf<Position.PositionNoteView>()
        positionNoteRoom.forEach { noteRoom ->
            with(PositionNoteMapper()) {
                val noteView =
                    noteRoom.roomToView()
                list.add(noteView)
            }
        }
        return list
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
