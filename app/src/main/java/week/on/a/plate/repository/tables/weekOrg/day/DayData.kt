package week.on.a.plate.repository.tables.weekOrg.day

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.core.data.week.DayInWeekData


@Entity
data class DayData(
    @PrimaryKey(autoGenerate = true)
    val dayId: Long = 0,
    val date: Int,
    @TypeConverters(DayInWeekDataTypeConverter::class)
    val dayInWeek: DayInWeekData,
    // ссылка на принадлежность дня к неделе
    val weekId:Long,
)