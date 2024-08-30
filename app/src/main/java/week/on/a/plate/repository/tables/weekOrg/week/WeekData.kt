package week.on.a.plate.repository.tables.weekOrg.week

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class WeekData(
    @PrimaryKey(autoGenerate = true)
    val weekId: Long = 0,
    //ссылка на нераспределенноео
    val selectionId: Long,
)
