package week.on.a.plate.repository.tables.recipe.ingredientInRecipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IngredientInRecipeRoom(
    val recipeId: Long,
    val ingredientId: Long,
    val description: String,
    val count:Double
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
