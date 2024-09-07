package week.on.a.plate.repository.tables.weekOrg.position.positionIngredient

import androidx.room.Entity
import androidx.room.PrimaryKey
import week.on.a.plate.repository.tables.weekOrg.position.PositionRoom


@Entity
data class PositionIngredientRoom(
    val ingredientInRecipeId: Long,
    val selectionId: Long,
) : PositionRoom() {
    @PrimaryKey(autoGenerate = true)
    var positionIngredientId: Long = 0
}
