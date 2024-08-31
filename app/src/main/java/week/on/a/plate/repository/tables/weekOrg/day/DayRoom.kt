package week.on.a.plate.repository.tables.weekOrg.day

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.core.data.week.DayInWeekData
import java.time.LocalDate


@Entity
data class DayRoom(
    @TypeConverters(DateTypeConverter::class)
    val date: LocalDate,
    @TypeConverters(DayInWeekDataTypeConverter::class)
    val dayInWeek: DayInWeekData,
    // ссылка на принадлежность дня к неделе
    val weekId:Long,
){
    @PrimaryKey(autoGenerate = true)
    var dayId: Long = 0
}