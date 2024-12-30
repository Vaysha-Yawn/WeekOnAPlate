package week.on.a.plate.data.dataView.week

import week.on.a.plate.R
import java.time.LocalTime

class CategoriesSelection(val fullName: Int, val stdTime: LocalTime)

val ForWeek = CategoriesSelection(R.string.for_week, LocalTime.of(0, 0))
val NonPosed = CategoriesSelection(R.string.for_day, LocalTime.of(0, 0))

fun getStdCategoriesSelection() = listOf(
    CategoriesSelection(R.string.tag_breakfast, LocalTime.of(8, 0)), CategoriesSelection(R.string.tag_lunch, LocalTime.of(13, 0)),
    CategoriesSelection(R.string.tag_dinner, LocalTime.of(18, 0)), CategoriesSelection(R.string.tag_snack, LocalTime.of(16, 0))
)

