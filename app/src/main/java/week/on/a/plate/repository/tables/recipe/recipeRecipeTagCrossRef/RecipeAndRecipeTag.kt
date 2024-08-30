package week.on.a.plate.repository.tables.recipe.recipeRecipeTagCrossRef

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import week.on.a.plate.repository.tables.recipe.recipe.Recipe
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTag

data class RecipeAndRecipeTag(
    @Embedded val recipe: Recipe,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeTagId",
        associateBy = Junction(RecipeRecipeTagCrossRef::class)
    )
    val recipeTags: List<RecipeTag>
)

data class RecipeTagAndRecipe(
    @Embedded val recipeTag: RecipeTag,
    @Relation(
        parentColumn = "recipeTagId",
        entityColumn = "recipeId",
        associateBy = Junction(RecipeRecipeTagCrossRef::class)
    )
    val recipe: List<Recipe>,
)