package week.on.a.plate.data.repository.room.filters.recipeTag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeTagRoom(
    var recipeTagCategoryId: Long,
    val tagName: String,
) {
    @PrimaryKey(autoGenerate = true)
    var recipeTagId: Long = 0
}
