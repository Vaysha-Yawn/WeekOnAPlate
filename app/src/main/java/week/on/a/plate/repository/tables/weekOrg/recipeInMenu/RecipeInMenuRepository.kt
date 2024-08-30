package week.on.a.plate.repository.tables.weekOrg.recipeInMenu

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class RecipeInMenuRepository(val dao: RecipeInMenuDAO) {

    fun read(): Flow<List<RecipeInMenu>> = dao.getAll()

    suspend fun create(recipeInMenu: RecipeInMenu) {
        dao.insert(recipeInMenu)
    }

    suspend fun update(recipeInMenu: RecipeInMenu) {
        dao.update(recipeInMenu)
    }

    suspend fun delete(recipeInMenu: RecipeInMenu) {
        dao.delete(recipeInMenu)
    }

}




