package week.on.a.plate.repository.tables.recipe.recipe

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipe


data class RecipeAndIngredientInRecipe(
    @Embedded val recipe: Recipe,
    @Relation(
         parentColumn = "recipeId",
         entityColumn = "recipeId"
    )
    val ingredientInRecipe: List<IngredientInRecipe>
)