package week.on.a.plate.data.repository.tables.filters.ingredient

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.data.dataView.recipe.IngredientView
import javax.inject.Inject

class IngredientRepository @Inject constructor(
    private val ingredientDAO: IngredientDAO,
) {
    private val mapper = IngredientMapper()
    suspend fun insert(categoryId:Long, img: String, name: String,  measure:String): Long {
        return ingredientDAO.insert(IngredientRoom(categoryId, img, name, measure))
    }
    suspend fun update(oldId: Long,categoryId:Long, img: String, name: String,  measure:String){
        ingredientDAO.update(IngredientRoom(categoryId, img, name, measure).apply { ingredientId =  oldId})
    }
    suspend fun delete(id:Long){
        ingredientDAO.deleteById(id)
    }
    suspend fun getById(id:Long): IngredientView? {
        return with(mapper) { ingredientDAO.findByID(id)?.roomToView() }
    }

    fun getByIdFlow(id:Long): Flow<IngredientView?> {
        return ingredientDAO.findByIDFlow(id).map { with(mapper) { it?.roomToView() } }
    }
}