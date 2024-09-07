package week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftIngredientCrossRef

import androidx.room.Entity


@Entity(primaryKeys = ["draftId", "ingredientId"])
data class DraftAndIngredientCrossRef(
    val draftId: Long,
    val ingredientId: Long
)
