package week.on.a.plate.data.repository.tables.menu.position.positionRecipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import week.on.a.plate.data.repository.tables.menu.position.PositionRoom


@Entity
data class PositionRecipeRoom(
    var recipeId: Long,
    val recipeName: String,
    var portionsCount: Int,
    val selectionId: Long,
) : PositionRoom() {
    @PrimaryKey(autoGenerate = true)
    var recipeInMenuId: Long = 0
}
