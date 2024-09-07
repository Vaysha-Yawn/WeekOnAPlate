package week.on.a.plate.repository.tables.weekOrg.selectionInDay

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientRoom
import week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu.PositionRecipeRoom


data class SelectionAndPositionIngredient(
    @Embedded val selectionRoom: SelectionRoom,
    @Relation(
         parentColumn = "selectionId",
         entityColumn = "selectionId"
    )
    val positionIngredientRooms: List<PositionIngredientRoom>
)