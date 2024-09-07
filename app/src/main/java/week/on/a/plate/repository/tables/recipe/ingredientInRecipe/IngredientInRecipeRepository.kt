package week.on.a.plate.repository.tables.recipe.ingredientInRecipe

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class IngredientInRecipeRepository(val dao:IngredientInRecipeDAO) {

    fun read(): Flow<List<IngredientInRecipeRoom>> = dao.getAll()

    suspend fun create(ingredientInRecipeRoom: IngredientInRecipeRoom) {
        dao.insert(ingredientInRecipeRoom)
    }

    suspend fun update(ingredientInRecipeRoom: IngredientInRecipeRoom) {
        dao.update(ingredientInRecipeRoom)
    }

    suspend fun delete(ingredientInRecipeRoom: IngredientInRecipeRoom) {
        dao.delete(ingredientInRecipeRoom)
    }

}




