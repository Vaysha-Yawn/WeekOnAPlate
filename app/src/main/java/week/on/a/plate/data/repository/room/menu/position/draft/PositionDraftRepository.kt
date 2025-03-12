package week.on.a.plate.data.repository.room.menu.position.draft


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.room.filters.ingredient.IngredientDAO
import week.on.a.plate.data.repository.room.filters.ingredient.IngredientMapper
import week.on.a.plate.data.repository.room.filters.recipeTag.RecipeTagDAO
import week.on.a.plate.data.repository.room.filters.recipeTag.RecipeTagMapper
import week.on.a.plate.data.repository.utils.combineSafeIfFlowIsEmpty
import week.on.a.plate.data.repository.utils.updateListOfEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PositionDraftRepository @Inject constructor(
    private val positionDraftDAO: PositionDraftDAO,
    private val ingredientDAO: IngredientDAO,
    private val recipeTagDAO: RecipeTagDAO,
) {

    private val mapper = PositionDraftMapper()
    private val ingredientMapper = IngredientMapper()
    private val tagMapper = RecipeTagMapper()

    suspend fun getAllInSel(selectionId: Long): List<Position> {
        return positionDraftDAO.getAllInSel(selectionId).map { draftRoom ->

            val listTags = positionDraftDAO.getDraftAndTagByDraftId(draftRoom.draftId)
                .tags.map {
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


    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllInSelFlow(selectionId: Long): Flow<List<Position>> {
        return positionDraftDAO.getAllInSelFlow(selectionId)
            .flatMapLatest { draftList ->
                val listFlow = draftList.map { draft -> getPositionStateFlow(draft) }
                listFlow.combineSafeIfFlowIsEmpty()
            }
    }

    private fun getPositionStateFlow(draft: PositionDraftRoom): Flow<Position> {
        val ingredientsFlow = getIngredientsFlow(draft.draftId)
        val tagsFlow = getTagsFlow(draft.draftId)

        return combine(tagsFlow, ingredientsFlow) { tags, ingredients ->
            with(mapper) {
                draft.roomToView(tags = tags, ingredients = ingredients)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getIngredientsFlow(draftId: Long): Flow<List<IngredientView>> {
        return positionDraftDAO.getDraftAndIngredientByDraftIdFlow(draftId)
            .onEmpty { emit(emptyList()) }
            .flatMapLatest { list ->
                val listFlowIngredients = list.map {
                    ingredientDAO.findByIDFlow(it.ingredientId).onEmpty { emit(null) }
                }
                val flowIngredients = listFlowIngredients.combineSafeIfFlowIsEmpty()
                flowIngredients.map { ingredients ->
                    ingredients.mapNotNull { with(ingredientMapper) { it?.roomToView() } }
                }
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getTagsFlow(draftId: Long): Flow<List<RecipeTagView>> {
        return positionDraftDAO.getDraftAndTagByDraftIdFlow(draftId).onEmpty { emit(emptyList()) }
            .flatMapLatest { list ->
                val listFlowTags =
                    list.map { recipeTagDAO.findByIDFlow(it.recipeTagId).onEmpty { emit(null) } }
                val flowTags = listFlowTags.combineSafeIfFlowIsEmpty()
                flowTags.map { tags ->
                    tags.mapNotNull { with(tagMapper) { it?.roomToView() } }
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
        tags: List<RecipeTagView>, ingredients: List<IngredientView>
    ) {
        updateListOfEntity(
            oldList = oldDraft.tags,
            newList = tags,
            findSameInList = { tagView, list ->
                list.find { tag -> tag.id == tagView.id }
            },
            insertAction = { tags ->
                val tagRefs = PositionDraftMapper().genTagCrossRef(oldDraft.id, tags.id)
                positionDraftDAO.insertDraftAndTagCrossRef(tagRefs)
            },
            deleteAction = { tag -> positionDraftDAO.deleteByIdTag(tag.id) },
            updateAction = { tag ->
                positionDraftDAO.deleteByIdTag(tag.id)
                val tagRefs = PositionDraftMapper().genTagCrossRef(oldDraft.id, tag.id)
                positionDraftDAO.insertDraftAndTagCrossRef(tagRefs)
            },
        )

        updateListOfEntity(
            oldList = oldDraft.ingredients,
            newList = ingredients,
            findSameInList = { ingredient, list ->
                list.find { ingredientView -> ingredientView.ingredientId == ingredient.ingredientId }
            },
            insertAction = { ingredient ->
                val ingredientRefs = PositionDraftMapper().genIngredientCrossRef(
                    oldDraft.id,
                    ingredient.ingredientId
                )
                positionDraftDAO.insertDraftAndIngredientCrossRef(ingredientRefs)
            },
            deleteAction = { ingredient ->
                positionDraftDAO.deleteByIdIngredient(ingredient.ingredientId)
            },
            updateAction = { ingredient ->
                positionDraftDAO.deleteByIdIngredient(ingredient.ingredientId)
                val ingredientRefs = PositionDraftMapper().genIngredientCrossRef(
                    oldDraft.id,
                    ingredient.ingredientId
                )
                positionDraftDAO.insertDraftAndIngredientCrossRef(ingredientRefs)
            },
        )
    }
}

