package week.on.a.plate.data.repository.tables.filters.ingredient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IngredientRoom(
    var ingredientCategoryId: Long,
    val img: String,
    val name: String,
    val measure:String
){
    @PrimaryKey(autoGenerate = true)
    var ingredientId: Long = 0
}
