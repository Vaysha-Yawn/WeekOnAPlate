package week.on.a.plate.repository.tables.weekOrg.position.positionDraft

import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftIngredientCrossRef.DraftAndIngredientCrossRef
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.draftTagCrossRef.DraftAndTagCrossRef


class PositionDraftMapper() {
    fun PositionDraftRoom.roomToView(
        tags: List<RecipeTagView>,
        ingredients: List<IngredientView>,
    ): Position.PositionDraftView =
        Position.PositionDraftView(
            id = this.draftId,
            tags, ingredients
        )

    fun Position.PositionDraftView.viewToRoom(selectionId: Long): PositionDraftRoom =
        PositionDraftRoom(selectionId)

    fun genTagCrossRef(newPositionDraftID:Long, tagId:Long, ): DraftAndTagCrossRef {
        return DraftAndTagCrossRef(newPositionDraftID, tagId)
    }

    fun genIngredientCrossRef(newPositionDraftID:Long, ingredientId:Long): DraftAndIngredientCrossRef {
        return DraftAndIngredientCrossRef(newPositionDraftID, ingredientId)
    }
}
