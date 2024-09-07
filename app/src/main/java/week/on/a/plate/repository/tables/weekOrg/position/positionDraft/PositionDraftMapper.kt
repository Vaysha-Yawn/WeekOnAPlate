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
            id = this.id,
            tags, ingredients
        )

    fun Position.PositionDraftView.viewToRoom(selectionId: Long): PositionDraftRoom =
        PositionDraftRoom(selectionId)

    fun Position.PositionDraftView.genTagCrossRefs(newPositionDraftID:Long): List<DraftAndTagCrossRef> {
        val list = mutableListOf<DraftAndTagCrossRef>()
        this.tags.forEach { tag->
            list.add(DraftAndTagCrossRef(newPositionDraftID, tag.id))
        }
        return list
    }

    fun Position.PositionDraftView.genIngredientCrossRefs(newPositionDraftID:Long): List<DraftAndIngredientCrossRef> {
        val list = mutableListOf<DraftAndIngredientCrossRef>()
        this.ingredients.forEach { ingredient->
            list.add(DraftAndIngredientCrossRef(newPositionDraftID, ingredient.ingredientId))
        }
        return list
    }
}
