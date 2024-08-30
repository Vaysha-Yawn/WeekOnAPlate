package week.on.a.plate.repository.tables.recipe.ingredient

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientRepository (val dao: IngredientDAO) {

    fun read(): Flow<List<Ingredient>> = dao.getAll()

    suspend fun create(ingredient: Ingredient) {
        dao.insert(ingredient)
    }

    suspend fun update(ingredient: Ingredient) {
        dao.update(ingredient)
    }

    suspend fun delete(ingredient: Ingredient) {
        dao.delete(ingredient)
    }

}




