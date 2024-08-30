package week.on.a.plate.repository.tables.recipe.recipeRecipeTagCrossRef

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(primaryKeys = ["recipeId", "recipeTagId"])
data class RecipeRecipeTagCrossRef(
    val recipeId: Long,
    val recipeTagId: Long
)
