package week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftIngredientCrossRef

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import week.on.a.plate.repository.tables.recipe.ingredient.IngredientRoom
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTagRoom
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftRoom

data class DraftAndIngredient(
    @Embedded val draft: PositionDraftRoom,
    @Relation(
        parentColumn = "draftId",
        entityColumn = "ingredientId",
        associateBy = Junction(DraftAndIngredientCrossRef::class)
    )
    val ingredients: List<IngredientRoom>
)