package week.on.a.plate.data.repository.room.filters.ingredientCategory

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.repository.room.filters.ingredient.IngredientDAO
import week.on.a.plate.data.repository.room.filters.ingredient.IngredientMapper
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
        ingredientRooms.forEach {
            daoIngredient.update(it.apply { ingredientCategoryId = 1L })
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




