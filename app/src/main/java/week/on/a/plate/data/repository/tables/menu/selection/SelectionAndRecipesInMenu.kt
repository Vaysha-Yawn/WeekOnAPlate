package week.on.a.plate.data.repository.tables.menu.selection

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.data.repository.tables.menu.position.positionRecipe.PositionRecipeRoom


data class SelectionAndRecipesInMenu(
    @Embedded val selectionRoom: SelectionRoom,
    @Relation(
         parentColumn = "selectionId",
         entityColumn = "selectionId"
    )
    val positionRecipeRooms: List<PositionRecipeRoom>
)