package week.on.a.plate.data.repository.room.menu.position.draft.draftTagCrossRef

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import week.on.a.plate.data.repository.room.filters.recipeTag.RecipeTagRoom
import week.on.a.plate.data.repository.room.menu.position.draft.PositionDraftRoom

data class DraftAndTag(
    @Embedded val draft: PositionDraftRoom,
    @Relation(
        parentColumn = "draftId",
        entityColumn = "recipeTagId",
        associateBy = Junction(DraftAndTagCrossRef::class)
    )
    val tags: List<RecipeTagRoom>
)