package week.on.a.plate.data.repository.tables.menu.day

import androidx.room.TypeConverter
import week.on.a.plate.data.dataView.week.DayInWeekData

class DayInWeekDataTypeConverter {
    @TypeConverter
    fun toState(value: String) = enumValueOf<DayInWeekData>(value)
    @TypeConverter
    fun fromState(value: DayInWeekData) = value.name
}