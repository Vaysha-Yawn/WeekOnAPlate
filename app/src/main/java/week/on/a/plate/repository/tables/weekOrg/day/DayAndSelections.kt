package week.on.a.plate.repository.tables.weekOrg.day

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionInDay


data class DayAndSelections(
    @Embedded val day: DayData,
    @Relation(
         parentColumn = "dayId",
         entityColumn = "dayId"
    )
    val selections: List<SelectionInDay>,
)