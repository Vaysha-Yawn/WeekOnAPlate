package week.on.a.plate.repository.tables.recipe.recipe

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class RecipeRepository(val dao: RecipeDAO) {

    fun read(): Flow<List<Recipe>> = dao.getAll()

    suspend fun create(recipe: Recipe) {
        dao.insert(recipe)
    }

    suspend fun update(recipe: Recipe) {
        dao.update(recipe)
    }

    suspend fun delete(recipe: Recipe) {
        dao.delete(recipe)
    }

}




