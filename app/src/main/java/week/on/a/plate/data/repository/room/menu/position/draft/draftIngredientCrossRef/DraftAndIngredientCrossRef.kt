package week.on.a.plate.data.repository.room.menu.position.draft.draftIngredientCrossRef

import androidx.room.Entity


@Entity(primaryKeys = ["draftId", "ingredientId"])
data class DraftAndIngredientCrossRef(
    val draftId: Long,
    val ingredientId: Long
)
