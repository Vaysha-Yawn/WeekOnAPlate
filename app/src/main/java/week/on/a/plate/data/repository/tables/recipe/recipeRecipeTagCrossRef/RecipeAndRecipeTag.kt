package week.on.a.plate.data.repository.tables.recipe.recipeRecipeTagCrossRef

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRoom
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagRoom

data class RecipeAndRecipeTag(
    @Embedded val recipeRoom: RecipeRoom,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeTagId",
        associateBy = Junction(RecipeRecipeTagCrossRef::class)
    )
    val recipeTagRooms: List<RecipeTagRoom>
)