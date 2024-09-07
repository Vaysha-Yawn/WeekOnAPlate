package week.on.a.plate.repository.tables.weekOrg.position.positionNote

import androidx.room.Entity
import androidx.room.PrimaryKey
import week.on.a.plate.repository.tables.weekOrg.position.PositionRoom


@Entity
data class PositionNoteRoom(
    val note: String,
    val selectionId: Long,
) : PositionRoom() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
