package week.on.a.plate.data.repository.tables.menu.selection

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.data.repository.tables.menu.position.positionIngredient.PositionIngredientRoom


data class SelectionAndPositionIngredient(
    @Embedded val selectionRoom: SelectionRoom,
    @Relation(
         parentColumn = "selectionId",
         entityColumn = "selectionId"
    )
    val positionIngredientRooms: List<PositionIngredientRoom>
)