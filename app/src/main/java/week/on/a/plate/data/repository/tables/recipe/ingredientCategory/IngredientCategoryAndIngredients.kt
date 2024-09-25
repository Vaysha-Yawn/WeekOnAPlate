package week.on.a.plate.data.repository.tables.recipe.ingredientCategory

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.data.repository.tables.recipe.ingredient.IngredientRoom


data class IngredientCategoryAndIngredients(
    @Embedded val ingredientCategoryRoom: IngredientCategoryRoom,
    @Relation(
         parentColumn = "ingredientCategoryId",
         entityColumn = "ingredientCategoryId"
    )
    val ingredientRooms: List<IngredientRoom>,
)