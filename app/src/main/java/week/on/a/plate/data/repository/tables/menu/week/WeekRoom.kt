package week.on.a.plate.data.repository.tables.menu.week

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class WeekRoom(
    val selectionId: Long,
){
    @PrimaryKey(autoGenerate = true)
    var weekId: Long = 0
}
