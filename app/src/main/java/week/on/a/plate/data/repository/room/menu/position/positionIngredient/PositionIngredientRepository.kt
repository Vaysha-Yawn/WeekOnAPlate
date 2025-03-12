package week.on.a.plate.data.repository.room.menu.position.positionIngredient


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEmpty
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.room.filters.ingredient.IngredientMapper
import week.on.a.plate.data.repository.room.recipe.ingredientInRecipe.IngredientAndIngredientInRecipe
import week.on.a.plate.data.repository.room.recipe.ingredientInRecipe.IngredientInRecipeDAO
import week.on.a.plate.data.repository.room.recipe.ingredientInRecipe.IngredientInRecipeMapper
import week.on.a.plate.data.repository.room.recipe.ingredientInRecipe.IngredientInRecipeRoom
import week.on.a.plate.data.repository.utils.combineSafeIfFlowIsEmpty
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PositionIngredientRepository @Inject constructor(
    private val positionIngredientDAO: PositionIngredientDAO,
    private val ingredientInRecipeDAO: IngredientInRecipeDAO,
) {
    private val positionIngredientMapper = PositionIngredientMapper()
    private val ingredientInRecipeMapper = IngredientInRecipeMapper()
    private val ingredientMapper = IngredientMapper()

    suspend fun getAllInSel(selectionId: Long): List<Position> {
        return positionIngredientDAO.getAllInSel(selectionId).map { position ->
            val ingredient =
                ingredientInRecipeDAO.getIngredientAndIngredientInRecipe(position.ingredientInRecipeId)
            ingredientToPosition(ingredient, position)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getAllInSelFlow(selectionId: Long): Flow<List<Position>> {
        val flowPositions =
            positionIngredientDAO.getAllInSelFlow(selectionId).onEmpty { emit(listOf()) }
        val result = flowPositions.flatMapLatest { listPositions ->
            val listFlow = listPositions.map { position ->
                val flowIngredientCrossRef =
                    ingredientInRecipeDAO.getIngredientAndIngredientInRecipeFlow(position.ingredientInRecipeId)
                        .onEmpty { emit(null) }
                flowIngredientCrossRef.mapNotNull { ingredientAndIngredientInRecipe ->
                    if (ingredientAndIngredientInRecipe == null) return@mapNotNull null
                    ingredientToPosition(ingredientAndIngredientInRecipe, position)
                }
            }
            listFlow.combineSafeIfFlowIsEmpty()
        }
        return result
    }

    private fun ingredientToPosition(
        ingredient: IngredientAndIngredientInRecipe,
        position: PositionIngredientRoom
    ): Position.PositionIngredientView {
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
