package week.on.a.plate.repository.tables.recipe.ingredientInRecipe

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.core.data.recipe.IngredientView


data class IngredientAndIngredientInRecipe(
    @Embedded val ingredientView: IngredientView,
    @Relation(
         parentColumn = "ingredientId",
         entityColumn = "ingredientId"
    )
    val ingredientInRecipe: IngredientInRecipe
)