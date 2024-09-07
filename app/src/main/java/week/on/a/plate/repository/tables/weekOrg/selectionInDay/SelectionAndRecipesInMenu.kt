package week.on.a.plate.repository.tables.weekOrg.selectionInDay

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu.PositionRecipeRoom


data class SelectionAndRecipesInMenu(
    @Embedded val selectionRoom: SelectionRoom,
    @Relation(
         parentColumn = "selectionId",
         entityColumn = "selectionId"
    )
    val positionRecipeRooms: List<PositionRecipeRoom>
)