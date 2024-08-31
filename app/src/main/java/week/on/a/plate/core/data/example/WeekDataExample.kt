package week.on.a.plate.core.data.example

import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.data.week.DayInWeekData
import week.on.a.plate.core.data.week.RecipeInMenuView
import week.on.a.plate.core.data.recipe.RecipeStateView
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.core.data.week.WeekView
import java.time.LocalDate

val EmptyWeek = WeekView(
    0,
    SelectionView(0, "", mutableListOf()),
    mutableListOf(
        DayView(1,
            LocalDate.of(2024,8,26), DayInWeekData.Mon, emptyDay
        ),
        DayView(2,
            LocalDate.of(2024,8,27), DayInWeekData.Tues, emptyDay
        ),
        DayView(3,
            LocalDate.of(2024,8,28), DayInWeekData.Wed, emptyDay
        ),
        DayView(4,
            LocalDate.of(2024,8,29), DayInWeekData.Thurs, emptyDay
        ),
        DayView(5,
            LocalDate.of(2024,8,30), DayInWeekData.Fri, emptyDay
        ),
        DayView(6,
            LocalDate.of(2024,8,31), DayInWeekData.Sat, emptyDay
        ),
        DayView(7,
            LocalDate.of(2024,8,1), DayInWeekData.Sun, emptyDay
        ),
    )
)

val WeekDataExample = WeekView(0,
    SelectionView(7,
        "Нераспределенное",
        mutableListOf(RecipeInMenuView(0, RecipeStateView.Created, shortRecipe, 3))
    ),
    mutableListOf(
        DayView(1,
            LocalDate.of(2024,8,26), DayInWeekData.Mon, dayMenuExample
        ),
        DayView(2,
            LocalDate.of(2024,8,27), DayInWeekData.Tues, dayMenuExample
        ),
        DayView(3,
            LocalDate.of(2024,8,28), DayInWeekData.Wed, mutableListOf()
        ),
        DayView(4,
            LocalDate.of(2024,8,29), DayInWeekData.Thurs, dayMenuExample
        ),
        DayView(5,
            LocalDate.of(2024,8,30), DayInWeekData.Fri, dayMenuExample
        ),
        DayView(6,
            LocalDate.of(2024,8,31), DayInWeekData.Sat, dayMenuExample
        ),
        DayView(7,
            LocalDate.of(2024,8,1), DayInWeekData.Sun, dayMenuExample
        ),
    )
)