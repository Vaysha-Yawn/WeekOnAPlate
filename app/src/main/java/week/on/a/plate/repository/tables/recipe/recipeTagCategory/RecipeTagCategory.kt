package week.on.a.plate.repository.tables.recipe.recipeTagCategory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeTagCategory(
    @PrimaryKey(autoGenerate = true)
    val recipeTagCategoryId: Long = 0,
    var name: String,
)
