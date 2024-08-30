package week.on.a.plate.core.data.example

import week.on.a.plate.core.data.week.DayData
import week.on.a.plate.core.data.week.DayInWeekData
import week.on.a.plate.core.data.week.RecipeInMenu
import week.on.a.plate.core.data.recipe.RecipeState
import week.on.a.plate.core.data.week.SelectionInDayData
import week.on.a.plate.core.data.week.WeekData

val WeekDataExample = WeekData(
    SelectionInDayData(
        "Нераспределенное",
        mutableListOf(RecipeInMenu(0, RecipeState.Created, recipeTom, 3))
    ),
    mutableListOf(
        DayData(
            20, DayInWeekData.Mon, dayMenuExample
        ),
        DayData(
            21, DayInWeekData.Tues, dayMenuExample
        ),
        DayData(
            22, DayInWeekData.Wed, mutableListOf()
        ),
        DayData(
            23, DayInWeekData.Thurs, dayMenuExample
        ),
        DayData(
            24, DayInWeekData.Fri, dayMenuExample
        ),
        DayData(
            25, DayInWeekData.Sat, dayMenuExample
        ),
        DayData(
            26, DayInWeekData.Sun, dayMenuExample
        ),
    )
)