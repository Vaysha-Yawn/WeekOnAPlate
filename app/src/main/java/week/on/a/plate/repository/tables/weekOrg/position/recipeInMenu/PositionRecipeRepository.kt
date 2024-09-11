package week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import week.on.a.plate.core.data.recipe.RecipeView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.RecipeShortView
import javax.inject.Inject


class PositionRecipeRepository @Inject constructor(
    private val recipeInMenuDAO: RecipeInMenuDAO
) {
    fun getAllInSel(selectionId: Long): Flow<List<Position>> {
        return recipeInMenuDAO.getAllInSel(selectionId)
            .transform<List<PositionRecipeRoom>, List<Position>> {
                val list = mutableListOf<Position>()
                it.forEach { recipeInMenu ->
                    with(RecipeInMenuMapper()) {
                        val newRecipeInMenuView =
                            recipeInMenu.roomToView(
                                recipeInMenu.recipeId, recipeInMenu.recipeName
                            )
                        list.add(newRecipeInMenuView)
                    }
                }
                emit(list)
            }
    }

    suspend fun insert(recipeView: Position.PositionRecipeView, selectionId: Long): Long {
        val positionRoom = with(RecipeInMenuMapper()) { recipeView.viewToRoom(selectionId) }
        return recipeInMenuDAO.insert(positionRoom)
    }

    suspend fun update(id: Long, recipe: RecipeShortView, count: Int, selectionId: Long) {
        recipeInMenuDAO.update(
            PositionRecipeRoom(recipe.id, recipe.name, count, selectionId).apply {
                this.recipeInMenuId = id
            }
        )
    }

    suspend fun delete(id: Long) {
        recipeInMenuDAO.deleteById(id)
    }


}
