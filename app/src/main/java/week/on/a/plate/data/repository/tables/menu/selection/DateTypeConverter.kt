package week.on.a.plate.data.repository.tables.menu.selection

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


class DateTypeConverter {
    @TypeConverter
    fun toState(value: String) =  LocalDate.parse(value)
    @TypeConverter
    fun fromState(value: LocalDate) = value.toString()
}

class LocalTimeTypeConverter {
    @TypeConverter
    fun toState(value: String) =  LocalTime.parse(value)
    @TypeConverter
    fun fromState(value: LocalTime) = value.toString()
}

class LocalDateTimeTypeConverter {
    @TypeConverter
    fun toState(value: String) =  LocalDateTime.parse(value)
    @TypeConverter
    fun fromState(value: LocalDateTime) = value.toString()
}