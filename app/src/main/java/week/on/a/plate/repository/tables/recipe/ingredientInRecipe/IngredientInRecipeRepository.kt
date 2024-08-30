package week.on.a.plate.repository.tables.recipe.ingredientInRecipe

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class IngredientInRecipeRepository(val dao:IngredientInRecipeDAO) {

    fun read(): Flow<List<IngredientInRecipe>> = dao.getAll()

    suspend fun create(ingredientInRecipe: IngredientInRecipe) {
        dao.insert(ingredientInRecipe)
    }

    suspend fun update(ingredientInRecipe: IngredientInRecipe) {
        dao.update(ingredientInRecipe)
    }

    suspend fun delete(ingredientInRecipe: IngredientInRecipe) {
        dao.delete(ingredientInRecipe)
    }

}




