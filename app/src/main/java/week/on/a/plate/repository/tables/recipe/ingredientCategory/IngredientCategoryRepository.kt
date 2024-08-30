package week.on.a.plate.repository.tables.recipe.ingredientCategory

import kotlinx.coroutines.flow.Flow

import javax.inject.Singleton

@Singleton
class IngredientCategoryRepository(val dao: IngredientCategoryDAO) {

    fun read(): Flow<List<IngredientCategory>> = dao.getAll()

    suspend fun create(ingredientCategory: IngredientCategory) {
        dao.insert(ingredientCategory)
    }

    suspend fun update(ingredientCategory: IngredientCategory) {
        dao.update(ingredientCategory)
    }

    suspend fun delete(ingredientCategory: IngredientCategory) {
        dao.delete(ingredientCategory)
    }
}




