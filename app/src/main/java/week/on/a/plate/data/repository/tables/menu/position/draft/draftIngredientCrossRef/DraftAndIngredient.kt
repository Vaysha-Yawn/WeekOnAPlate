package week.on.a.plate.data.repository.tables.menu.position.draft.draftIngredientCrossRef

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import week.on.a.plate.data.repository.tables.recipe.ingredient.IngredientRoom
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftRoom

data class DraftAndIngredient(
    @Embedded val draft: PositionDraftRoom,
    @Relation(
        parentColumn = "draftId",
        entityColumn = "ingredientId",
        associateBy = Junction(DraftAndIngredientCrossRef::class)
    )
    val ingredients: List<IngredientRoom>
)