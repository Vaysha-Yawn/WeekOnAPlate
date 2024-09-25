package week.on.a.plate.data.repository.tables.recipe.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeRoom(
    val name: String,
    val description: String,
    val img:String,
    val prepTime: Int,
    val allTime: Int,
    val standardPortionsCount: Int,
    val link: String
){
    @PrimaryKey(autoGenerate = true)
    var recipeId: Long = 0
}
