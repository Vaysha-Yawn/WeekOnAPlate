package week.on.a.plate.data.repository.room.menu.position.positionRecipe

import androidx.room.Entity
import androidx.room.PrimaryKey
import week.on.a.plate.data.repository.room.menu.position.PositionRoom


@Entity
data class PositionRecipeRoom(
    var recipeId: Long,
    var portionsCount: Int,
    val selectionId: Long,
) : PositionRoom() {
    @PrimaryKey(autoGenerate = true)
    var recipeInMenuId: Long = 0
}
