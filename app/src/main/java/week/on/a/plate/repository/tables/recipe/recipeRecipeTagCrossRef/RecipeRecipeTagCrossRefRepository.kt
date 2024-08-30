package week.on.a.plate.repository.tables.recipe.recipeRecipeTagCrossRef

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class RecipeRecipeTagCrossRefRepository(val dao: RecipeRecipeTagCrossRefDAO) {

    fun read(): Flow<List<RecipeRecipeTagCrossRef>> = dao.getAll()

    suspend fun create(recipeRef: RecipeRecipeTagCrossRef) {
        dao.insert(recipeRef)
    }

    suspend fun update(recipeRef: RecipeRecipeTagCrossRef) {
        dao.update(recipeRef)
    }

    suspend fun delete(recipeRef: RecipeRecipeTagCrossRef) {
        dao.delete(recipeRef)
    }

}




