package week.on.a.plate.data.repository.room.recipe.recipe

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.data.repository.room.recipe.recipeStep.RecipeStepRoom


data class RecipeAndRecipeSteps(
    @Embedded val recipeRoom: RecipeRoom,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeId"
    )
    val recipeStepRoom: List<RecipeStepRoom>
)