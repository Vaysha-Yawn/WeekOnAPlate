package week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.recipe.recipe.RecipeRoom


data class RecipeInMenuAndRecipe(
    @Embedded val recipeRoom: RecipeRoom,
    @Relation(
         parentColumn = "recipeId",
         entityColumn = "recipeId"
    )
    val positionRecipeRoom: PositionRecipeRoom,
)