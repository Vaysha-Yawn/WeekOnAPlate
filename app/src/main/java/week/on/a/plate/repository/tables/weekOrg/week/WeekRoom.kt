package week.on.a.plate.repository.tables.weekOrg.week

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class WeekRoom(
    //ссылка на нераспределенноео
    val selectionId: Long,
){
    @PrimaryKey(autoGenerate = true)
    var weekId: Long = 0
}
