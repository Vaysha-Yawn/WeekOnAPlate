package week.on.a.plate.data.repository.room.recipe.recipeStep

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import week.on.a.plate.data.repository.utils.ListIntConverter

@Entity
data class RecipeStepRoom(
    val recipeId: Long = 0,
    val description: String,
    val image: String,
    val timer: Long,
    // remove
    /*    val start:Long,
        val duration:Long,*/
    @TypeConverters(ListIntConverter::class)
    val ingredientsPinned: List<Long>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
