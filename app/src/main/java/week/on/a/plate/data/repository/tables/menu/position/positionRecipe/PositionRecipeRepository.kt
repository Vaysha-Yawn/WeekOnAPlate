package week.on.a.plate.data.repository.tables.menu.position.positionRecipe


import androidx.compose.runtime.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeDAO
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRoom
import week.on.a.plate.data.repository.utils.flowToStateWithMap
import javax.inject.Inject

class PositionRecipeRepository @Inject constructor(
    private val positionRecipeDAO: PositionRecipeDAO,
    private val recipeDao: RecipeDAO,
) {
    private val mapper = RecipeInMenuMapper()

    suspend fun getAllInSel(selectionId: Long): List<Position> {
        return positionRecipeDAO.getAllInSel(selectionId).map { recipeInMenu ->
            val recipe = recipeDao.getRecipeById(recipeInMenu.recipeId)
            recipeToRecipePos(recipeInMenu, recipe)
        }
    }

    fun getAllInSelFlow(selectionId: Long, scope:CoroutineScope): Flow<List<State<Position>>> {
        return positionRecipeDAO.getAllInSelFlow(selectionId).map{
            it.map { recipeInMenu ->
                val firstValue = scope.async {
                    val recipeRoom = recipeDao.getRecipeById(recipeInMenu.recipeId)
                    recipeToRecipePos(recipeInMenu, recipeRoom)
                }

                recipeDao.getRecipeByIdFlow(recipeInMenu.recipeId).flowToStateWithMap(firstValue.await(), scope){
                    recipeToRecipePos(recipeInMenu, this)
                }
            }
        }
    }

    private fun recipeToRecipePos(recipeInMenu: PositionRecipeRoom, recipeRoom: RecipeRoom): Position.PositionRecipeView{
       return with(mapper) {
            recipeInMenu.roomToView(
                recipeInMenu.recipeId, recipeRoom.name, recipeRoom.img
            )
        }
    }

    suspend fun insert(recipeView: Position.PositionRecipeView, selectionId: Long): Long {
        val positionRoom = with(mapper) { recipeView.viewToRoom(selectionId) }
        return positionRecipeDAO.insert(positionRoom)
    }

    suspend fun update(id: Long, recipe: RecipeShortView, count: Int, selectionId: Long) {
        positionRecipeDAO.update(
            PositionRecipeRoom(recipe.id, count, selectionId).apply {
                this.recipeInMenuId = id
            }
        )
    }

    suspend fun delete(id: Long) {
        positionRecipeDAO.deleteById(id)
    }

    suspend fun deleteByRecipeId(id: Long) {
        positionRecipeDAO.deleteByRecipeId(id)
    }


}
