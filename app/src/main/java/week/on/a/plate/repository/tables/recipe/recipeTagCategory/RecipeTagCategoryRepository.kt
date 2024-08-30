package week.on.a.plate.repository.tables.recipe.recipeTagCategory

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class RecipeTagCategoryRepository(val dao: RecipeTagCategoryDAO) {

    fun read(): Flow<List<RecipeTagCategory>> = dao.getAll()

    suspend fun create(recipeTagCategory: RecipeTagCategory) {
        dao.insert(recipeTagCategory)
    }

    suspend fun update(recipeTagCategory: RecipeTagCategory) {
        dao.update(recipeTagCategory)
    }

    suspend fun delete(recipeTagCategory: RecipeTagCategory) {
        dao.delete(recipeTagCategory)
    }

}




