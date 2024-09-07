package week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftTagCrossRef

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTagRoom
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftRoom

data class DraftAndTag(
    @Embedded val draft: PositionDraftRoom,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeTagId",
        associateBy = Junction(DraftAndTagCrossRef::class)
    )
    val tags: List<RecipeTagRoom>
)