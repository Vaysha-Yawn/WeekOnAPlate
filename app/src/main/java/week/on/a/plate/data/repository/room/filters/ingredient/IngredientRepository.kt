package week.on.a.plate.data.repository.room.filters.ingredient

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.repository.room.menu.position.draft.draftIngredientCrossRef.DraftAndIngredientCrossRefDAO
import week.on.a.plate.data.repository.room.menu.position.positionIngredient.PositionIngredientDAO
import week.on.a.plate.data.repository.room.recipe.ingredientInRecipe.IngredientInRecipeDAO
import week.on.a.plate.data.repository.room.shoppingList.ShoppingItemDAO
import javax.inject.Inject

class IngredientRepository @Inject constructor(
    private val ingredientDAO: IngredientDAO,
    private val draftAndIngredientCrossRefDAO: DraftAndIngredientCrossRefDAO,
    private val ingredientInRecipeDAO: IngredientInRecipeDAO,
    private val shoppingItemDAO: ShoppingItemDAO,
    private val positionIngredientDAO: PositionIngredientDAO,
) {
    private val mapper = IngredientMapper()
    suspend fun insert(categoryId: Long, img: String, name: String, measure: String): Long {
        return ingredientDAO.insert(IngredientRoom(categoryId, img, name, measure))
    }

    suspend fun update(oldId: Long, categoryId: Long, img: String, name: String, measure: String) {
        ingredientDAO.update(IngredientRoom(categoryId, img, name, measure).apply {
            ingredientId = oldId
        })
    }

    suspend fun delete(id: Long) {
        ingredientDAO.deleteById(id)
        draftAndIngredientCrossRefDAO.deleteByIngredient(id)
        val ingredientsInRecipe = ingredientInRecipeDAO.getAllByIngredientId(id)
        ingredientInRecipeDAO.deleteByIngredientId(id)
        ingredientsInRecipe.forEach {
            shoppingItemDAO.deleteAllByIngredientInRecipeId(it.id)
            positionIngredientDAO.deleteAllByIngredientInRecipeId(it.id)
        }
    }

    suspend fun getById(id: Long): IngredientView? {
        return with(mapper) { ingredientDAO.findByID(id)?.roomToView() }
    }

    fun getByIdFlow(id: Long): Flow<IngredientView?> {
        return ingredientDAO.findByIDFlow(id).map { with(mapper) { it?.roomToView() } }
    }
}