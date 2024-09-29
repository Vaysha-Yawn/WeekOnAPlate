package week.on.a.plate.data.repository.tables.filters.ingredientCategory

import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientMapper
import javax.inject.Singleton

@Singleton
class IngredientCategoryRepository(val dao: IngredientCategoryDAO) {

    private val ingredientMapper = IngredientMapper()
    private val ingredientCategoryMapper = IngredientCategoryMapper()

    suspend fun getAllIngredientsByCategoriesForFilters(): List<IngredientCategoryView> {
        val listAllRoom = dao.getAllIngredientCategoryAndIngredients()
        val listResult = mutableListOf<IngredientCategoryView>()
        listAllRoom.forEach { categoryAndIngredientRoom ->
            val listIngredients = mutableListOf<IngredientView>()
            categoryAndIngredientRoom.ingredientRooms.forEach { ingredientRoom ->
                val ingredientView = with(ingredientMapper) {
                    ingredientRoom.roomToView()
                }
                listIngredients.add(ingredientView)
            }
            val categoryView = with(ingredientCategoryMapper) {
                categoryAndIngredientRoom.ingredientCategoryRoom.roomToView(listIngredients)
            }
            listResult.add(categoryView)
        }
        return listResult
    }

    suspend fun create(name:String):Long {
        val category = IngredientCategoryRoom(name)
        return dao.insert(category)
    }

    suspend fun updateName(newName: String, id: Long) {
        dao.update(IngredientCategoryRoom(newName).apply { this.ingredientCategoryId=id })
    }

    suspend fun delete(id: Long) {
        dao.deleteById(id)
    }
}




