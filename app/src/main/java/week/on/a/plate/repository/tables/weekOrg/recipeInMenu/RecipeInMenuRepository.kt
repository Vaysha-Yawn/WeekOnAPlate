package week.on.a.plate.repository.tables.weekOrg.recipeInMenu

import kotlinx.coroutines.flow.Flow
import week.on.a.plate.core.data.recipe.RecipeView
import week.on.a.plate.core.data.week.RecipeInMenuView
import javax.inject.Singleton

@Singleton
class RecipeInMenuRepository(val dao: RecipeInMenuDAO) {

    fun read(): Flow<List<RecipeInMenuRoom>> = dao.getAll()

    fun getRecipeInMenuAndRecipe(recipeId:Long): Flow<RecipeInMenuAndRecipe> = dao.getRecipeInMenuAndRecipe(recipeId)

    suspend fun insert(selId:Long, recipeInMenu: RecipeInMenuView){
        val recipeInMenuRoom = with(RecipeInMenuMapper()) {
            recipeInMenu.viewToRoom(selId)
        }
        dao.insert(recipeInMenuRoom)

        //todo insert recipe ???
    }


    suspend fun update(recipeInMenuRoom: RecipeInMenuRoom) = dao.update(recipeInMenuRoom)


    suspend fun delete(recipeInMenuRoom: RecipeInMenuRoom) = dao.delete(recipeInMenuRoom)


}




