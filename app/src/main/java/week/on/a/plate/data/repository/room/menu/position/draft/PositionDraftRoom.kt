package week.on.a.plate.data.repository.room.menu.position.draft

import androidx.room.Entity
import androidx.room.PrimaryKey
import week.on.a.plate.data.repository.room.menu.position.PositionRoom


@Entity
class PositionDraftRoom(val selectionId: Long) : PositionRoom() {
    @PrimaryKey(autoGenerate = true)
    var draftId: Long = 0
}
