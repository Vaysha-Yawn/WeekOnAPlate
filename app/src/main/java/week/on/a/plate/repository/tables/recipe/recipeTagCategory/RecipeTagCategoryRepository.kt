package week.on.a.plate.repository.tables.recipe.recipeTagCategory

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class RecipeTagCategoryRepository(val dao: RecipeTagCategoryDAO) {

    fun read(): Flow<List<RecipeTagCategoryRoom>> = dao.getAll()

    suspend fun create(recipeTagCategoryRoom: RecipeTagCategoryRoom) {
        dao.insert(recipeTagCategoryRoom)
    }

    suspend fun update(recipeTagCategoryRoom: RecipeTagCategoryRoom) {
        dao.update(recipeTagCategoryRoom)
    }

    suspend fun delete(recipeTagCategoryRoom: RecipeTagCategoryRoom) {
        dao.delete(recipeTagCategoryRoom)
    }

}




