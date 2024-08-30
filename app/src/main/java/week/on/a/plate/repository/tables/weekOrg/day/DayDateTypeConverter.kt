package week.on.a.plate.repository.tables.weekOrg.day

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


class DayDateTypeConverter {
    @TypeConverter
    fun toState(value: String) =  Date(value)
    @TypeConverter
    fun fromState(value: Date) = value.toString()

}