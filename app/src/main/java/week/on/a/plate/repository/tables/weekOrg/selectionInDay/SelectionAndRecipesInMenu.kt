package week.on.a.plate.repository.tables.weekOrg.selectionInDay

import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.coroutines.flow.Flow
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuRoom


data class SelectionAndRecipesInMenu(
    @Embedded val selectionRoom: SelectionRoom,
    @Relation(
         parentColumn = "selectionId",
         entityColumn = "selectionId"
    )
    val recipeInMenuRooms: List<RecipeInMenuRoom>
)