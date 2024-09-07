package week.on.a.plate.repository.tables.recipe.ingredientCategory

import kotlinx.coroutines.flow.Flow

import javax.inject.Singleton

@Singleton
class IngredientCategoryRepository(val dao: IngredientCategoryDAO) {

    fun read(): Flow<List<IngredientCategoryRoom>> = dao.getAll()

    suspend fun create(ingredientCategoryRoom: IngredientCategoryRoom) {
        dao.insert(ingredientCategoryRoom)
    }

    suspend fun update(ingredientCategoryRoom: IngredientCategoryRoom) {
        dao.update(ingredientCategoryRoom)
    }

    suspend fun delete(ingredientCategoryRoom: IngredientCategoryRoom) {
        dao.delete(ingredientCategoryRoom)
    }
}




