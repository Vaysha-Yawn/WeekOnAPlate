package week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftTagCrossRef

import androidx.room.Entity


@Entity(primaryKeys = ["draftId", "tagId"])
data class DraftAndTagCrossRef(
    val draftId: Long,
    val tagId: Long
)
