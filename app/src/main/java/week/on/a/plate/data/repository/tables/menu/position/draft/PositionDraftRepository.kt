package week.on.a.plate.data.repository.tables.menu.position.draft


import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientMapper
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagMapper
import week.on.a.plate.data.utils.updateListOfEntity
import javax.inject.Inject


class PositionDraftRepository @Inject constructor(
    private val positionDraftDAO: PositionDraftDAO
) {

    private val mapper = PositionDraftMapper()
    private val ingredientMapper = IngredientMapper()
    private val tagMapper = RecipeTagMapper()

    suspend fun getAllInSel(selectionId: Long): List<Position> {
        return positionDraftDAO.getAllInSel(selectionId).map { draftRoom ->

            val draftAndTags =
                positionDraftDAO.getDraftAndTagByDraftId(draftRoom.draftId)

            val listTags = draftAndTags.tags.map {
                with(tagMapper) { it.roomToView() }
            }

            val listIngredients =
                positionDraftDAO.getDraftAndIngredientByDraftId(draftRoom.draftId).ingredients.map {
                    with(ingredientMapper) {
                        it.roomToView()
                    }
                }

            with(mapper) {
                draftRoom.roomToView(
                    tags = listTags,
                    ingredients = listIngredients
                )
            }
        }
    }

    suspend fun insert(position: Position.PositionDraftView, selectionId: Long) {
        val positionRoom = with(mapper) {
            position.viewToRoom(selectionId)
        }
        val newId = positionDraftDAO.insert(positionRoom)
        position.tags.forEach {
            val tagId = it.id
            val tagRefs = with(mapper) {
                genTagCrossRef(newId, tagId)
            }
            positionDraftDAO.insertDraftAndTagCrossRef(tagRefs)
        }

        position.ingredients.forEach {
            val ingredientRefs = with(mapper) {
                genIngredientCrossRef(newId, it.ingredientId)
            }
            positionDraftDAO.insertDraftAndIngredientCrossRef(ingredientRefs)
        }
    }

    suspend fun delete(draftId: Long) {
        positionDraftDAO.deleteById(draftId)
    }

    suspend fun update(
        oldDraft: Position.PositionDraftView,
        filters: Pair<List<RecipeTagView>, List<IngredientView>>
    ) {
        updateListOfEntity(
            oldList = oldDraft.tags,
            newList = filters.first,
            insertAction = { listTagsAdd ->
                listTagsAdd.forEach {
                    val tagRefs = PositionDraftMapper().genTagCrossRef(oldDraft.id, it.id)
                    positionDraftDAO.insertDraftAndTagCrossRef(tagRefs)
                }
            },
            deleteAction = { tag ->
                positionDraftDAO.deleteByIdTag(tag.id)
            }
        )

        updateListOfEntity(
            oldList = oldDraft.ingredients,
            newList = filters.second,
            insertAction = { listIngredientsAdd ->
                listIngredientsAdd.forEach {
                    val ingredientRefs = PositionDraftMapper().genIngredientCrossRef(oldDraft.id, it.ingredientId)
                    positionDraftDAO.insertDraftAndIngredientCrossRef(ingredientRefs)
                }
            },
            deleteAction = { ingredient ->
                positionDraftDAO.deleteByIdIngredient(ingredient.ingredientId)
            }
        )
    }
}