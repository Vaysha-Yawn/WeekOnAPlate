package week.on.a.plate.repository.tables.recipe.ingredientCategory

import androidx.room.Entity
import androidx.room.PrimaryKey
import week.on.a.plate.core.data.recipe.Ingredient

@Entity
data class IngredientCategory(
    @PrimaryKey(autoGenerate = true)
    val ingredientCategoryId: Long = 0,
    val name: String,
)
