package week.on.a.plate.data.repository.tables.menu.position.draft.draftTagCrossRef

import androidx.room.Entity


@Entity(primaryKeys = ["draftId", "recipeTagId"])
data class DraftAndTagCrossRef(
    val draftId: Long,
    val recipeTagId: Long
)
