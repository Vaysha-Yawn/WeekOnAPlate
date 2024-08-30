package week.on.a.plate.repository.tables.recipe.ingredient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val ingredientId: Long = 0,
    val ingredientCategoryId: Long,
    val img: String,
    val name: String,
    val measure:String
)
