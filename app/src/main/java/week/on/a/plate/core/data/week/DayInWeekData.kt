package week.on.a.plate.core.data.week

import java.time.LocalDate

enum class DayInWeekData(val fullName: String, val shortName: String) {
    Mon("Понедельник", "пн"), Tues("Вторник", "вт"), Wed("Среда", "ср"), Thurs("Четверг", "чт"),
    Fri("Пятница", "пт"), Sat("Суббота", "сб"), Sun("Воскресенье", "вс");

    companion object{
        fun localeDateToDayInWeekData(date:LocalDate): DayInWeekData {
            val d = date.dayOfWeek.value
            return DayInWeekData.entries[d-1]
        }
    }
}