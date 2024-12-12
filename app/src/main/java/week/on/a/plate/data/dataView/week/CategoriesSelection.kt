package week.on.a.plate.data.dataView.week

import java.time.LocalTime

class CategoriesSelection(val fullName: String, val stdTime: LocalTime)
val ForWeek = CategoriesSelection("На неделю", LocalTime.of(0, 0))
val NonPosed = CategoriesSelection("На день", LocalTime.of(0, 0))

val stdCategoriesSelection = listOf(
    CategoriesSelection("Завтрак", LocalTime.of(8, 0)), CategoriesSelection("Oбед", LocalTime.of(13, 0)),
    CategoriesSelection("Yжин", LocalTime.of(18, 0)), CategoriesSelection("Перекус", LocalTime.of(16, 0))
)

