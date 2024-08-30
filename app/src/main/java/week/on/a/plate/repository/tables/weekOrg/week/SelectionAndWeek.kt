package week.on.a.plate.repository.tables.weekOrg.week

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionInDay


data class SelectionAndWeek(
    @Embedded val week: WeekData,
    @Relation(
         parentColumn = "selectionId",
         entityColumn = "selectionId"
    )
    val selection: SelectionInDay,
)