package week.on.a.plate.data.repository.tables.menu.selection

import androidx.room.TypeConverter
import java.time.LocalDate


class DateTypeConverter {
    @TypeConverter
    fun toState(value: String) =  LocalDate.parse(value)
    @TypeConverter
    fun fromState(value: LocalDate) = value.toString()

}