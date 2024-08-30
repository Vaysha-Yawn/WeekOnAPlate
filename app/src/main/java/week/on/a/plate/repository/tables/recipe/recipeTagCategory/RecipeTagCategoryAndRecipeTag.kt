package week.on.a.plate.repository.tables.recipe.recipeTagCategory

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTag


data class RecipeTagCategoryAndRecipeTag(
    @Embedded val ingredientCategory: RecipeTagCategory,
    @Relation(
         parentColumn = "recipeTagCategoryId",
         entityColumn = "recipeTagCategoryId"
    )
    val ingredients: List<RecipeTag>,
)