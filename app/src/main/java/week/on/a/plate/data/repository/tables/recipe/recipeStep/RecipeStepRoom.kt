package week.on.a.plate.data.repository.tables.recipe.recipeStep

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeStepRoom(
    val recipeId: Long = 0,
    val title:String,
    val description: String,
    val image:String,
    val timer:Long
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
