package week.on.a.plate.screens.base.menu.domain.dbusecase

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.room.menu.position.note.PositionNoteRepository
import javax.inject.Inject

class AddNoteToDBUseCase @Inject constructor(
    private val noteRepository: PositionNoteRepository
) {
    suspend operator fun invoke(text: String, selId: Long) {
        noteRepository.insert(
            Position.PositionNoteView(0, text, selId),
            selId
        )
    }
}

class EditNoteInDBUseCase @Inject constructor(
    private val noteRepository: PositionNoteRepository
) {
    suspend operator fun invoke(position: Position.PositionNoteView) {
        noteRepository.update(
            position.idPos,
            position.note, position.selectionId
        )
    }
}

class DeleteNoteInDBUseCase @Inject constructor(
    private val noteRepository: PositionNoteRepository
) {
    suspend operator fun invoke(position: Position.PositionNoteView) {
        noteRepository.delete(position.idPos)
    }
}