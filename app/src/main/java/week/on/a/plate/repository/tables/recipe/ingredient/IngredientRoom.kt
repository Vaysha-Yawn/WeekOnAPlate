package week.on.a.plate.repository.tables.recipe.ingredient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IngredientRoom(
    val ingredientCategoryId: Long,
    val img: String,
    val name: String,
    val measure:String
){
    @PrimaryKey(autoGenerate = true)
    var ingredientId: Long = 0
}
