package week.on.a.plate.repository.tables.recipe.recipe

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.recipe.recipeStep.RecipeStep


data class RecipeAndRecipeSteps(
    @Embedded val recipe: Recipe,
    @Relation(
         parentColumn = "recipeId",
         entityColumn = "recipeId"
    )
    val recipeStep: List<RecipeStep>
)