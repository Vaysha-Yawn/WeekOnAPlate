package week.on.a.plate.data.dataView.example

import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.data.dataView.week.DayView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.WeekView
import java.time.LocalDate
import java.time.LocalTime

val WeekDataExample = WeekView(0,
    SelectionView(7,
        CategoriesSelection.ForWeek.fullName,
        LocalDate.now(), 0, true, LocalTime.of(0,0), mutableListOf(),
    ),
    mutableListOf()
)