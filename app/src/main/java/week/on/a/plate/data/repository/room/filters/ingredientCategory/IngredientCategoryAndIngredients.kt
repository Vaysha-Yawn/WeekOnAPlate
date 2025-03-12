package week.on.a.plate.data.repository.room.filters.ingredientCategory

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.data.repository.room.filters.ingredient.IngredientRoom


data class IngredientCategoryAndIngredients(
    @Embedded val ingredientCategoryRoom: IngredientCategoryRoom,
    @Relation(
        parentColumn = "ingredientCategoryId",
        entityColumn = "ingredientCategoryId"
    )
    val ingredientRooms: List<IngredientRoom>,
)