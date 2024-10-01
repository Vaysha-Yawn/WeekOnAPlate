package week.on.a.plate.data.repository.tables.shoppingList

import androidx.room.Entity
import androidx.room.PrimaryKey
import week.on.a.plate.data.repository.tables.menu.position.PositionRoom


@Entity
data class ShoppingItemRoom(
    val ingredientInRecipeId: Long,
    val checked: Boolean,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
