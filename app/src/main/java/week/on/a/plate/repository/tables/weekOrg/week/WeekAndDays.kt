package week.on.a.plate.repository.tables.weekOrg.week

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.weekOrg.day.DayRoom


data class WeekAndDays(
    @Embedded val week: WeekRoom,
    @Relation(
         parentColumn = "weekId",
         entityColumn = "weekId"
    )
    val days: List<DayRoom>
)