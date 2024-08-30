package week.on.a.plate.repository.tables.weekOrg.day

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.core.data.week.DayInWeekData
import java.util.Date


@Entity
data class DayData(
    @PrimaryKey(autoGenerate = true)
    val dayId: Long = 0,
    @TypeConverters(DayDateTypeConverter::class)
    val date: Date,
    @TypeConverters(DayInWeekDataTypeConverter::class)
    val dayInWeek: DayInWeekData,
    // ссылка на принадлежность дня к неделе
    val weekId:Long,
)