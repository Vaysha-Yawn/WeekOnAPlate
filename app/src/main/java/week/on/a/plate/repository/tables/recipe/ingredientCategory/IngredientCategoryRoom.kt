package week.on.a.plate.repository.tables.recipe.ingredientCategory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IngredientCategoryRoom(
    val name: String,
){
    @PrimaryKey(autoGenerate = true)
    var ingredientCategoryId: Long = 0
}
