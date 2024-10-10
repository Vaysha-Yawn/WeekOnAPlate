package week.on.a.plate.data.dataView.week

import java.time.LocalTime

enum class CategoriesSelection(val fullName: String, val stdTime: LocalTime, ) {
    ForWeek("На неделю", LocalTime.of(0, 0)), NonPosed("На день", LocalTime.of(0, 0)),
    Breakfast("Завтрак", LocalTime.of(8, 0)), Lunch("Oбед", LocalTime.of(13, 0)),
    Dinner("Yжин", LocalTime.of(18, 0)), Snack("Перекус", LocalTime.of(16, 0))
}