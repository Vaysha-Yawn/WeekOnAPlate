package week.on.a.plate.data.repository.tables.recipe.recipe

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRoom


data class RecipeAndIngredientInRecipe(
    @Embedded val recipeRoom: RecipeRoom,
    @Relation(
         parentColumn = "recipeId",
         entityColumn = "recipeId"
    )
    val ingredientInRecipeRoom: List<IngredientInRecipeRoom>
)