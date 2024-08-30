package week.on.a.plate.repository.tables.weekOrg.week

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.weekOrg.day.DayData


data class WeekAndDays(
    @Embedded val week: WeekData,
    @Relation(
         parentColumn = "weekId",
         entityColumn = "weekId"
    )
    val days: List<DayData>
)