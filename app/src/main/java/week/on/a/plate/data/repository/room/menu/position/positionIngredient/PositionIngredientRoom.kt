package week.on.a.plate.data.repository.room.menu.position.positionIngredient

import androidx.room.Entity
import androidx.room.PrimaryKey
import week.on.a.plate.data.repository.room.menu.position.PositionRoom


@Entity
data class PositionIngredientRoom(
    val ingredientInRecipeId: Long,
    val selectionId: Long,
) : PositionRoom() {
    @PrimaryKey(autoGenerate = true)
    var positionIngredientId: Long = 0
}
