package week.on.a.plate.repository.tables.weekOrg.day

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionRoom


data class DayAndSelections(
    @Embedded val day: DayRoom,
    @Relation(
         parentColumn = "dayId",
         entityColumn = "dayId"
    )
    val selections: List<SelectionRoom>,
)