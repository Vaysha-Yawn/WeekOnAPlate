package week.on.a.plate.repository.tables.recipe.recipeStep

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeStep(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val recipeId: Long = 0,
    var title:String,
    var description: String,
    var image:String
)