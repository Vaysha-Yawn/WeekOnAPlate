package week.on.a.plate.data.repository.tables.menu.position.draft

import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.tables.menu.position.draft.draftIngredientCrossRef.DraftAndIngredientCrossRef
import week.on.a.plate.data.repository.tables.menu.position.draft.draftTagCrossRef.DraftAndTagCrossRef


class PositionDraftMapper() {
    fun PositionDraftRoom.roomToView(
        tags: List<RecipeTagView>,
        ingredients: List<IngredientView>,
    ): Position.PositionDraftView =
        Position.PositionDraftView(
            id = this.draftId,
            tags, ingredients, this.selectionId
        )

    fun Position.PositionDraftView.viewToRoom(selectionId:Long): PositionDraftRoom =
        PositionDraftRoom(selectionId)

    fun genTagCrossRef(newPositionDraftID:Long, tagId:Long, ): DraftAndTagCrossRef {
        return DraftAndTagCrossRef(newPositionDraftID, tagId)
    }

    fun genIngredientCrossRef(newPositionDraftID:Long, ingredientId:Long): DraftAndIngredientCrossRef {
        return DraftAndIngredientCrossRef(newPositionDraftID, ingredientId)
    }
}
