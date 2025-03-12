package week.on.a.plate.data.repository.room.menu.position.note

import androidx.room.Entity
import androidx.room.PrimaryKey
import week.on.a.plate.data.repository.room.menu.position.PositionRoom


@Entity
data class PositionNoteRoom(
    val note: String,
    val selectionId: Long,
) : PositionRoom() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
