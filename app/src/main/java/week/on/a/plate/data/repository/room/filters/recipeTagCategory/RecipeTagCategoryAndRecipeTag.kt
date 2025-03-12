package week.on.a.plate.data.repository.room.filters.recipeTagCategory

import androidx.room.Embedded
import androidx.room.Relation
import week.on.a.plate.data.repository.room.filters.recipeTag.RecipeTagRoom


data class RecipeTagCategoryAndRecipeTag(
    @Embedded val tagCategory: RecipeTagCategoryRoom,
    @Relation(
        parentColumn = "recipeTagCategoryId",
        entityColumn = "recipeTagCategoryId"
    )
    val tags: List<RecipeTagRoom>,
)