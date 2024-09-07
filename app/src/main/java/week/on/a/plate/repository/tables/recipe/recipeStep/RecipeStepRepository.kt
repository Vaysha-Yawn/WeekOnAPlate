package week.on.a.plate.repository.tables.recipe.recipeStep

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class RecipeStepRepository(val dao: RecipeStepDAO) {

    fun read(): Flow<List<RecipeStepRoom>> = dao.getAll()

    suspend fun create(recipeStepRoom: RecipeStepRoom) {
        dao.insert(recipeStepRoom)
    }

    suspend fun update(recipeStepRoom: RecipeStepRoom) {
        dao.update(recipeStepRoom)
    }

    suspend fun delete(recipeStepRoom: RecipeStepRoom) {
        dao.delete(recipeStepRoom)
    }

}




