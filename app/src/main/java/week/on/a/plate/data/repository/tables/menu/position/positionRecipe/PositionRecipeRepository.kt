package week.on.a.plate.data.repository.tables.menu.position.positionRecipe


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEmpty
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeDAO
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRoom
import week.on.a.plate.data.repository.utils.combineSafeIfFlowIsEmpty
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllInSelFlow(selectionId: Long): Flow<List<Position>> {
        val flowPositions =
            positionRecipeDAO.getAllInSelFlow(selectionId).onEmpty { emit(listOf()) }
        val result = flowPositions.flatMapLatest {
            val listFlow = it.map { recipeInMenu ->
                val flowRecipe =
                    recipeDao.getRecipeByIdFlow(recipeInMenu.recipeId).onEmpty { emit(null) }
                flowRecipe.mapNotNull { recipeRoom ->
                    if (recipeRoom == null) return@mapNotNull null
                    recipeToRecipePos(recipeInMenu, recipeRoom)
                }
            }
            listFlow.combineSafeIfFlowIsEmpty()
        }
        return result
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
