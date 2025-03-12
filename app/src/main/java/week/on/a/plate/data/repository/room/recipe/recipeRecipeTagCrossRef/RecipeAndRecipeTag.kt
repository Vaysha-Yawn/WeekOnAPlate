package week.on.a.plate.data.repository.room.recipe.recipeRecipeTagCrossRef

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import week.on.a.plate.data.repository.room.filters.recipeTag.RecipeTagRoom
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRoom

data class RecipeAndRecipeTag(
    @Embedded val recipeRoom: RecipeRoom,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeTagId",
        associateBy = Junction(RecipeRecipeTagCrossRef::class)
    )
    val recipeTagRooms: List<RecipeTagRoom>
)