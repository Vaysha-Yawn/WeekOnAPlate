package week.on.a.plate.repository.tables.recipe.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val recipeId: Long = 0,
    val name: String,
    val description: String,
    val img:String,
    val prepTime: Int,
    val allTime: Int,
    val standardPortionsCount: Int,
    val link: String
)
