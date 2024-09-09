package week.on.a.plate.repository.tables.weekOrg.position.positionDraft

import androidx.room.Entity
import androidx.room.PrimaryKey
import week.on.a.plate.repository.tables.weekOrg.position.PositionRoom


@Entity
class PositionDraftRoom(val selectionId: Long) : PositionRoom() {
    @PrimaryKey(autoGenerate = true)
    var draftId: Long = 0
}
