package week.on.a.plate.data.repository.room.menu.position.positionIngredient

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.data.repository.room.recipe.ingredientInRecipe.IngredientInRecipeRoom


data class PositionIngredientAndIngredientInRecipe(
    @Embedded val ingredientInRecipeRoom: IngredientInRecipeRoom,
    @Relation(
        parentColumn = "id",
        entityColumn = "positionIngredientId"
    )
    val positionIngredientRoom: PositionIngredientRoom,
)