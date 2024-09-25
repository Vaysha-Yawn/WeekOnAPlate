package week.on.a.plate.data.repository.tables.recipe.recipeTag

import javax.inject.Inject

class RecipeTagRepository @Inject constructor(
    private val recipeTagDAO: RecipeTagDAO,
) {
    suspend fun insert(name: String, categoryId:Long): Long {
        return recipeTagDAO.insert(RecipeTagRoom(categoryId, name))
    }
    suspend fun update(oldTag: RecipeTagRoom, name: String, categoryId:Long){
        //recipeTagDAO.update()
    }
    suspend fun delete(id:Long){
        recipeTagDAO.deleteById(id)
    }
    suspend fun getById(id:Long): RecipeTagRoom? {
        return recipeTagDAO.findByID(id)
    }
}