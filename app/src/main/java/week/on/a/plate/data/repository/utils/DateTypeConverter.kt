package week.on.a.plate.data.repository.utils

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

class ListIntConverter {
    @TypeConverter
    fun toState(value: String): List<Long> {
        var start = value
        val result = mutableListOf<Long>()
        while (start!=""){
            val n = start.substringBefore(' ').toLongOrNull()
            val firstB = start.indexOfFirst {it == ' '}
            if (n!=null){
                result.add(n)
            }
            start = start.substring(firstB+1 until start.length)
        }
        return result
    }

    @TypeConverter
    fun fromState(value: List<Long>): String {
        var out = ""
        value.forEach {
            out+=it.toString()
            out+=" "
        }
        return out
    }
}