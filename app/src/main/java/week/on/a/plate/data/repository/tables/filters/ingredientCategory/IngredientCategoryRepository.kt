package week.on.a.plate.data.repository.tables.filters.ingredientCategory

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientDAO
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientMapper
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.startCategoryName
import javax.inject.Inject


class IngredientCategoryRepository @Inject constructor(
    private val dao: IngredientCategoryDAO,
    private val daoIngredient: IngredientDAO,
) {

    private val ingredientMapper = IngredientMapper()
    private val ingredientCategoryMapper = IngredientCategoryMapper()

    fun getAllIngredientsByCategoriesForFilters(): Flow<List<IngredientCategoryView>> {
        return dao.getAllIngredientCategoryAndIngredients().map { listCategoryAndIngredientRoom ->
            listCategoryAndIngredientRoom.map { ingredientCategoryAndIngredients ->
                val listIngredientView =
                    ingredientCategoryAndIngredients.ingredientRooms.map { ingredientRoom ->
                        with(ingredientMapper) {
                            ingredientRoom.roomToView()
                        }
                    }
                with(ingredientCategoryMapper) {
                    ingredientCategoryAndIngredients.ingredientCategoryRoom.roomToView(
                        listIngredientView
                    )
                }
            }
        }
    }

    suspend fun create(name: String): Long {
        val category = IngredientCategoryRoom(name)
        return dao.insert(category)
    }

    suspend fun updateName(newName: String, id: Long) {
        dao.update(IngredientCategoryRoom(newName).apply { this.ingredientCategoryId = id })
    }

    suspend fun delete(id: Long) {
        dao.deleteById(id)
        val ingredientRooms = daoIngredient.getAllByCategoryId(id)
        val startCategory = dao.findCategoryByName(startCategoryName)!!
        ingredientRooms.forEach {
            daoIngredient.update(it.apply { ingredientCategoryId = startCategory.ingredientCategoryId })
        }
    }

    suspend fun getById(id: Long): IngredientCategoryView? {
        val t = dao.getById(id) ?: return null
        val list = t.ingredientRooms.map {
            with(ingredientMapper) {
                it.roomToView()
            }
        }
        return with(ingredientCategoryMapper) {
            t.ingredientCategoryRoom.roomToView(list)
        }
    }
}




