package week.on.a.plate.repository.tables.recipe.recipeTag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeTag(
    @PrimaryKey(autoGenerate = true)
    val recipeTagId: Long = 0,
    val recipeTagCategoryId: Long,
    val tagName: String,
    val isTypeOfMeal:Boolean,
)
