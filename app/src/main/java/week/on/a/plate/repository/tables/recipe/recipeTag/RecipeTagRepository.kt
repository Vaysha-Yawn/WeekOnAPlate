package week.on.a.plate.repository.tables.recipe.recipeTag

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class RecipeTagRepository(val dao: RecipeTagDAO) {

    fun read(): Flow<List<RecipeTag>> = dao.getAll()

    suspend fun create(recipeTag: RecipeTag) {
        dao.insert(recipeTag)
    }

    suspend fun update(recipeTag: RecipeTag) {
        dao.update(recipeTag)
    }

    suspend fun delete(recipeTag: RecipeTag) {
        dao.delete(recipeTag)
    }

}




