package week.on.a.plate.repository.tables.recipe.recipeTag

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class RecipeTagRepository(val dao: RecipeTagDAO) {

    fun read(): Flow<List<RecipeTagRoom>> = dao.getAll()

    suspend fun create(recipeTagRoom: RecipeTagRoom) {
        dao.insert(recipeTagRoom)
    }

    suspend fun update(recipeTagRoom: RecipeTagRoom) {
        dao.update(recipeTagRoom)
    }

    suspend fun delete(recipeTagRoom: RecipeTagRoom) {
        dao.delete(recipeTagRoom)
    }

}




