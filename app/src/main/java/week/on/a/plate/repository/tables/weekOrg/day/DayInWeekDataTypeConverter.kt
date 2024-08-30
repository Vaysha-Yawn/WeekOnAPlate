package week.on.a.plate.repository.tables.weekOrg.day

import androidx.room.TypeConverter
import week.on.a.plate.core.data.week.DayInWeekData

class DayInWeekDataTypeConverter {
    @TypeConverter
    fun toState(value: String) = enumValueOf<DayInWeekData>(value)
    @TypeConverter
    fun fromState(value: DayInWeekData) = value.name
}