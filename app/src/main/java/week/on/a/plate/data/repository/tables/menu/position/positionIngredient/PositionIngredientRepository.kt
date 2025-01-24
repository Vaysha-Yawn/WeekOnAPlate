package week.on.a.plate.data.repository.tables.menu.position.positionIngredient


import android.util.Log
import androidx.compose.runtime.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientMapper
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRoom
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientAndIngredientInRecipe
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeDAO
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeMapper
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRoom
import week.on.a.plate.data.repository.utils.flowToStateWithMap
import javax.inject.Inject


class PositionIngredientRepository @Inject constructor(
    private val positionIngredientDAO: PositionIngredientDAO,
    private val ingredientInRecipeDAO: IngredientInRecipeDAO,
) {
    private val positionIngredientMapper = PositionIngredientMapper()
    private val ingredientInRecipeMapper = IngredientInRecipeMapper()
    private val ingredientMapper = IngredientMapper()

    suspend fun getAllInSel(selectionId: Long): List<Position> {
        return positionIngredientDAO.getAllInSel(selectionId).map { position ->
            val ingredient = ingredientInRecipeDAO.getIngredientAndIngredientInRecipe(position.ingredientInRecipeId)
            ingredientToPosition(ingredient, position)
        }
    }

    fun getAllInSelFlow(selectionId: Long, scope: CoroutineScope): Flow<List<State<Position>>> {
        return positionIngredientDAO.getAllInSelFlow(selectionId).map{
            it.map  { position ->
                val ingredient = ingredientInRecipeDAO.getIngredientAndIngredientInRecipe(position.ingredientInRecipeId)
                val firstValue = ingredientToPosition(ingredient, position)

                val ingredientFlow = ingredientInRecipeDAO.getIngredientAndIngredientInRecipeFlow(position.ingredientInRecipeId)
                ingredientFlow.flowToStateWithMap(firstValue, scope){
                    ingredientToPosition(this, position)
                }
            }
        }
    }

    private fun ingredientToPosition(ingredient: IngredientAndIngredientInRecipe, position:PositionIngredientRoom):Position.PositionIngredientView{
        val ingredientView = with(ingredientMapper) { ingredient.ingredientRoom.roomToView() }
        val ingredientInMenuView = with(ingredientInRecipeMapper) {
            ingredient.ingredientInRecipeRoom.roomToView(ingredientView)
        }
        return with(positionIngredientMapper) {
            position.roomToView(ingredientInRecipe = ingredientInMenuView)
        }
    }

    suspend fun insert(position: Position.PositionIngredientView, selectionId: Long) {
        val ingredientInRecipeRoom = with(IngredientInRecipeMapper()) {
            position.ingredient.viewToRoom(0)
        }
        val newIngredientInRecipeId = ingredientInRecipeDAO.insert(ingredientInRecipeRoom)

        val positionRoom = with(positionIngredientMapper) {
            position.viewToRoom(newIngredientInRecipeId, selectionId)
        }
        positionIngredientDAO.insert(positionRoom)
    }

    suspend fun update(
        id: Long,
        ingredientInRecipeId: Long,
        ingredientId: Long,
        selectionId: Long,
        description: String,
        count: Double
    ) {
        positionIngredientDAO.update(
            PositionIngredientRoom(ingredientInRecipeId, selectionId).apply {
                this.positionIngredientId = id
            }
        )
        ingredientInRecipeDAO.update(
            IngredientInRecipeRoom(
                recipeId = 0,
                ingredientId = ingredientId,
                description = description,
                count = count
            ).apply {
                this.id = ingredientInRecipeId
            })
    }

    suspend fun delete(id: Long) {
        positionIngredientDAO.deleteById(id)
    }
}
