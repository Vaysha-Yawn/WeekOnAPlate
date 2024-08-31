package week.on.a.plate.repository.tables.weekOrg.recipeInMenu

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.recipe.recipe.Recipe


data class RecipeInMenuAndRecipe(
    @Embedded val recipe: Recipe,
    @Relation(
         parentColumn = "recipeId",
         entityColumn = "recipeId"
    )
    val recipeInMenuRoom: RecipeInMenuRoom,
)