package week.on.a.plate.data.repository.tables.menu.day

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.data.repository.tables.menu.selection.SelectionRoom


data class DayAndSelections(
    @Embedded val day: DayRoom,
    @Relation(
         parentColumn = "dayId",
         entityColumn = "dayId"
    )
    val selections: List<SelectionRoom>,
)