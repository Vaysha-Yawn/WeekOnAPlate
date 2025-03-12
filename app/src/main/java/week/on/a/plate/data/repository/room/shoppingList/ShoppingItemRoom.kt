package week.on.a.plate.data.repository.room.shoppingList

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ShoppingItemRoom(
    val ingredientInRecipeId: Long,
    val checked: Boolean,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
