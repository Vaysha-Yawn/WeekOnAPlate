package week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu

import androidx.room.Entity
import androidx.room.PrimaryKey
import week.on.a.plate.repository.tables.weekOrg.position.PositionRoom


@Entity
data class PositionRecipeRoom(
    val recipeId: Long,
    val recipeName: String,
    var portionsCount: Int,
    val selectionId: Long,
) : PositionRoom() {
    @PrimaryKey(autoGenerate = true)
    var recipeInMenuId: Long = 0
}
