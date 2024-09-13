package week.on.a.plate.repository.tables.weekOrg.position.positionNote


import week.on.a.plate.core.data.week.Position
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

    /*fun getAllInSel(selectionId: Long): Flow<List<Position.PositionNoteView>> {
        return  positionNoteDAO.getAllInSel(selectionId)
            .transform<List<PositionNoteRoom>, List<Position.PositionNoteView>> {
                val list = mutableListOf<Position.PositionNoteView>()
                it.forEach { noteRoom ->
                    with(PositionNoteMapper()) {
                        val noteView =
                            noteRoom.roomToView()
                        list.add(noteView)
                    }
                }
                emit(list)
            }
    }*/

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
