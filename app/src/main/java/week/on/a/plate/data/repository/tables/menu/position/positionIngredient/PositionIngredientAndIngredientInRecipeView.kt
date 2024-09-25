package week.on.a.plate.data.repository.tables.menu.position.positionIngredient

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRoom


data class PositionIngredientAndIngredientInRecipeView(
    @Embedded val ingredientInRecipeRoom: IngredientInRecipeRoom,
    @Relation(
         parentColumn = "id",
         entityColumn = "positionIngredientId"
    )
    val positionIngredientRoom: PositionIngredientRoom,
)