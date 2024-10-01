package week.on.a.plate.data.repository.tables.menu.position.positionIngredient


import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientMapper
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeDAO
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeMapper
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRoom
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
            val ingredient =
                ingredientInRecipeDAO.getIngredientAndIngredientInRecipe(position.positionIngredientId)

            val ingredientView =
                with(ingredientMapper) { ingredient.ingredientRoom.roomToView() }

            val ingredientInMenuView = with(ingredientInRecipeMapper) {
                ingredient.ingredientInRecipeRoom.roomToView(ingredientView)
            }

            with(positionIngredientMapper) {
                position.roomToView(ingredientInRecipe = ingredientInMenuView)
            }
        }
    }

    suspend fun insert(position: Position.PositionIngredientView, selectionId: Long) {
        val ingredientRoom = with(IngredientInRecipeMapper()) {
            position.ingredient.viewToRoom(0, position.ingredient.ingredientView.ingredientId)
        }
        val newIngredientInRecipeId = positionIngredientDAO.insert(ingredientRoom)

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
