package week.on.a.plate.data.repository.tables.menu.position.positionRecipe

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRoom


data class RecipeInMenuAndRecipe(
    @Embedded val recipeRoom: RecipeRoom,
    @Relation(
         parentColumn = "recipeId",
         entityColumn = "recipeId"
    )
    val positionRecipeRoom: PositionRecipeRoom,
)