package week.on.a.plate.repository.tables.recipe.ingredientInRecipe

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.core.data.recipe.Ingredient


data class IngredientAndIngredientInRecipe(
    @Embedded val ingredient: Ingredient,
    @Relation(
         parentColumn = "ingredientId",
         entityColumn = "ingredientId"
    )
    val ingredientInRecipe: IngredientInRecipe
)