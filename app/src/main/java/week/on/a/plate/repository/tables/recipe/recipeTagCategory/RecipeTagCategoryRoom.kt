package week.on.a.plate.repository.tables.recipe.recipeTagCategory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeTagCategoryRoom(
    var name: String,
){
    @PrimaryKey(autoGenerate = true)
    var recipeTagCategoryId: Long = 0
}
