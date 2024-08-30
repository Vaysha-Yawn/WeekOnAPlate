package week.on.a.plate.repository.tables.recipe.recipeStep

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class RecipeStepRepository(val dao: RecipeStepDAO) {

    fun read(): Flow<List<RecipeStep>> = dao.getAll()

    suspend fun create(recipeStep: RecipeStep) {
        dao.insert(recipeStep)
    }

    suspend fun update(recipeStep: RecipeStep) {
        dao.update(recipeStep)
    }

    suspend fun delete(recipeStep: RecipeStep) {
        dao.delete(recipeStep)
    }

}




