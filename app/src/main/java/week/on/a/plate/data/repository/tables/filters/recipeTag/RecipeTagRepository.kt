package week.on.a.plate.data.repository.tables.filters.recipeTag

import week.on.a.plate.data.dataView.recipe.RecipeTagView
import javax.inject.Inject

class RecipeTagRepository @Inject constructor(
    private val recipeTagDAO: RecipeTagDAO,
) {
    private val mapper = RecipeTagMapper()
    suspend fun insert(name: String, categoryId:Long): Long {
        return recipeTagDAO.insert(RecipeTagRoom(categoryId, name))
    }
    suspend fun update(oldTagId: Long, name: String, categoryId:Long){
        recipeTagDAO.update(RecipeTagRoom(categoryId, name).apply { recipeTagId =  oldTagId})
    }
    suspend fun delete(id:Long){
        recipeTagDAO.deleteById(id)
    }
    suspend fun getById(id:Long): RecipeTagView? {
        return with(mapper){
            recipeTagDAO.findByID(id)?.roomToView()
        }
    }
}