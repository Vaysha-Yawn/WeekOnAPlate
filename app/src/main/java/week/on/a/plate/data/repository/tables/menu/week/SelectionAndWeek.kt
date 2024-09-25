package week.on.a.plate.data.repository.tables.menu.week

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.data.repository.tables.menu.selection.SelectionRoom


data class SelectionAndWeek(
    @Embedded val week: WeekRoom,
    @Relation(
         parentColumn = "selectionId",
         entityColumn = "selectionId"
    )
    val selection: SelectionRoom,
)