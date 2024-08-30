package week.on.a.plate.repository.tables.recipe.ingredientInRecipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class IngredientInRecipe(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val recipeId: Long,
    val ingredientId: Long,
    val count:Double
)
