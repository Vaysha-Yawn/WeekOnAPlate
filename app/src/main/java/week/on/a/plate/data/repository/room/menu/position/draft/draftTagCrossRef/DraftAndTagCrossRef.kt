package week.on.a.plate.data.repository.room.menu.position.draft.draftTagCrossRef

import androidx.room.Entity


@Entity(primaryKeys = ["draftId", "recipeTagId"])
data class DraftAndTagCrossRef(
    val draftId: Long,
    val recipeTagId: Long
)
