package week.on.a.plate.repository.tables.weekOrg.day

import androidx.room.TypeConverter
import java.time.LocalDate


class DateTypeConverter {
    @TypeConverter
    fun toState(value: String) =  LocalDate.parse(value)
    @TypeConverter
    fun fromState(value: LocalDate) = value.toString()

}