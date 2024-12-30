package week.on.a.plate.data.dataView.example

import week.on.a.plate.data.dataView.week.ForWeek
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.WeekView
import java.time.LocalDateTime

val WeekDataExample = WeekView(0,
    SelectionView(7,
        "На неделю",
        LocalDateTime.now(), 0, true, mutableListOf(),
    ),
    mutableListOf()
)