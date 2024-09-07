package week.on.a.plate.repository.tables.recipe.recipeTagCategory

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTagRoom


data class RecipeTagCategoryAndRecipeTag(
    @Embedded val ingredientCategory: RecipeTagCategoryRoom,
    @Relation(
         parentColumn = "recipeTagCategoryId",
         entityColumn = "recipeTagCategoryId"
    )
    val ingredients: List<RecipeTagRoom>,
)