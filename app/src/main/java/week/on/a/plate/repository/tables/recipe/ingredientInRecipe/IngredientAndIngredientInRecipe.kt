package week.on.a.plate.repository.tables.recipe.ingredientInRecipe

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.repository.tables.recipe.ingredient.IngredientRoom


data class IngredientAndIngredientInRecipe(
    @Embedded val ingredientInRecipeRoom: IngredientInRecipeRoom,
    @Relation(
         parentColumn = "ingredientId",
         entityColumn = "ingredientId"
    )
    val ingredientRoom: IngredientRoom,
)