package week.on.a.plate.repository.tables.recipe.ingredient

import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
class IngredientRepository (val dao: IngredientDAO) {

    fun read(): Flow<List<IngredientRoom>> = dao.getAll()

    suspend fun create(ingredientRoom: IngredientRoom) {
        dao.insert(ingredientRoom)
    }

    suspend fun update(ingredientRoom: IngredientRoom) {
        dao.update(ingredientRoom)
    }

    suspend fun delete(ingredientRoom: IngredientRoom) {
        dao.delete(ingredientRoom)
    }

}




