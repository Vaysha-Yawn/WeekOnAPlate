package week.on.a.plate.data.repository.room.recipe.recipeRecipeTagCrossRef

import androidx.room.Entity


@Entity(primaryKeys = ["recipeId", "recipeTagId"])
data class RecipeRecipeTagCrossRef(
    val recipeId: Long,
    val recipeTagId: Long
)
