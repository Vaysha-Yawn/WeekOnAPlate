package week.on.a.plate.data.repository.tables.filters.recipeTag

import javax.inject.Inject

class RecipeTagRepository @Inject constructor(
    private val recipeTagDAO: RecipeTagDAO,
) {
    suspend fun insert(name: String, categoryId:Long): Long {
        return recipeTagDAO.insert(RecipeTagRoom(categoryId, name))
    }
    suspend fun update(oldTagId: Long, name: String, categoryId:Long){
        recipeTagDAO.update(RecipeTagRoom(categoryId, name).apply { recipeTagId =  oldTagId})
    }
    suspend fun delete(id:Long){
        recipeTagDAO.deleteById(id)
    }
    suspend fun getById(id:Long): RecipeTagRoom? {
        return recipeTagDAO.findByID(id)
    }
}