package week.on.a.plate.repository.tables.recipe.ingredientCategory

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.recipe.ingredient.Ingredient


data class IngredientCategoryAndIngredients(
    @Embedded val ingredientCategory: IngredientCategory,
    @Relation(
         parentColumn = "ingredientCategoryId",
         entityColumn = "ingredientCategoryId"
    )
    val ingredients: List<Ingredient>,
)