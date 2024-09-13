package week.on.a.plate.repository.tables.weekOrg.position.positionDraft


import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.repository.tables.recipe.ingredient.IngredientMapper
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTagMapper
import javax.inject.Inject


class PositionDraftRepository @Inject constructor(
    private val positionDraftDAO: PositionDraftDAO
) {

    suspend fun getAllInSel(selectionId: Long): List<Position> {
        val positionDraftRoom = positionDraftDAO.getAllInSel(selectionId)
        val list = mutableListOf<Position>()
        positionDraftRoom.forEach { draftRoom ->
            val draftAndTags =
                positionDraftDAO.getDraftAndTagByDraftId(draftRoom.draftId)
            val listTags = mutableListOf<RecipeTagView>()
            draftAndTags.tags.forEach {
                with(RecipeTagMapper()) {
                    val tagView = it.roomToView()
                    listTags.add(tagView)
                }
            }
            val draftAndIngredient =
                positionDraftDAO.getDraftAndIngredientByDraftId(draftRoom.draftId)
            val listIngredients = mutableListOf<IngredientView>()
            draftAndIngredient.ingredients.forEach {
                with(IngredientMapper()) {
                    val ingredientView = it.roomToView()
                    listIngredients.add(ingredientView)
                }
            }
            with(PositionDraftMapper()) {
                val draftView =
                    draftRoom.roomToView(
                        tags = listTags,
                        ingredients = listIngredients
                    )
                list.add(draftView)
            }
        }
        return list
    }

    /*fun getAllInSel(selectionId: Long): Flow<List<Position>> {
        return positionDraftDAO.getAllInSel(selectionId)
            .transform<List<PositionDraftRoom>, List<Position>> {
                val list = mutableListOf<Position>()
                it.forEach { draftRoom ->
                    val draftAndTags =
                        positionDraftDAO.getDraftAndTagByDraftId(draftRoom.draftId)
                    val listTags = mutableListOf<RecipeTagView>()
                    draftAndTags.tags.forEach {
                        with(RecipeTagMapper()) {
                            val tagView = it.roomToView()
                            listTags.add(tagView)
                        }
                    }
                    val draftAndIngredient =
                        positionDraftDAO.getDraftAndIngredientByDraftId(draftRoom.draftId)
                    val listIngredients = mutableListOf<IngredientView>()
                    draftAndIngredient.ingredients.forEach {
                        with(IngredientMapper()) {
                            val ingredientView = it.roomToView()
                            listIngredients.add(ingredientView)
                        }
                    }
                    with(PositionDraftMapper()) {
                        val draftView =
                            draftRoom.roomToView(
                                tags = listTags,
                                ingredients = listIngredients
                            )
                        list.add(draftView)
                    }

                }
                emit(list)
            }
    }*/

    suspend fun insert(position: Position.PositionDraftView, selectionId: Long): Long {
        val positionRoom = with(PositionDraftMapper()) {
            position.viewToRoom(selectionId)
        }
        return positionDraftDAO.insert(positionRoom)
    }

    suspend fun update(id: Long, selectionId: Long) {
        positionDraftDAO.update(
            PositionDraftRoom(selectionId).apply {
                this.draftId = id
            }
        )
    }

    suspend fun delete(draftId: Long) {
        positionDraftDAO.deleteById(draftId)
    }
}
