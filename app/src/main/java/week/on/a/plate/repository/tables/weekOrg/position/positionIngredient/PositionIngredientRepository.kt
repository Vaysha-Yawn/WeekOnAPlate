package week.on.a.plate.repository.tables.weekOrg.position.positionIngredient


import week.on.a.plate.core.data.week.Position
import week.on.a.plate.repository.tables.recipe.ingredient.IngredientMapper
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeDAO
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeMapper
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRoom
import javax.inject.Inject


class PositionIngredientRepository @Inject constructor(
    private val positionIngredientDAO: PositionIngredientDAO,
    private val ingredientInRecipeDAO: IngredientInRecipeDAO,
) {
    suspend fun getAllInSel(selectionId: Long): List<Position> {
        val positionsRoom = positionIngredientDAO.getAllInSel(selectionId)
        val list = mutableListOf<Position>()
        positionsRoom.forEach { position ->
            val ingredient =
                ingredientInRecipeDAO.getIngredientAndIngredientInRecipe(position.positionIngredientId)
            val ingredientInMenu = ingredient.ingredientInRecipeRoom
            val ingredientRoom = ingredient.ingredientRoom
            val ingredientView =
                with(IngredientMapper()) { ingredientRoom.roomToView() }
            val ingredientInMenuView = with(IngredientInRecipeMapper()) {
                ingredientInMenu.roomToView(ingredientView)
            }
            with(PositionIngredientMapper()) {
                val positionIngredientView =
                    position.roomToView(ingredientInRecipe = ingredientInMenuView)
                list.add(positionIngredientView)
            }
        }
        return list
    }


    /* fun getAllInSel(selectionId: Long): Flow<List<Position>> {
         return positionIngredientDAO.getAllInSel(selectionId)
             .transform<List<PositionIngredientRoom>, List<Position>> {
                 val list = mutableListOf<Position>()
                 it.forEach { position ->
                     val ingredient =
                         ingredientInRecipeDAO.getIngredientAndIngredientInRecipe(position.positionIngredientId)
                     val ingredientInMenu = ingredient.ingredientInRecipeRoom
                     val ingredientRoom = ingredient.ingredientRoom
                     val ingredientView =
                         with(IngredientMapper()) { ingredientRoom.roomToView() }
                     val ingredientInMenuView = with(IngredientInRecipeMapper()) {
                         ingredientInMenu.roomToView(ingredientView)
                     }
                     with(PositionIngredientMapper()) {
                         val positionIngredientView =
                             position.roomToView(ingredientInRecipe = ingredientInMenuView)
                         list.add(positionIngredientView)
                     }
                 }

                 emit(list)
             }
     }*/

    suspend fun insert(position: Position.PositionIngredientView, selectionId: Long): Long {
        val positionRoom = with(PositionIngredientMapper()) {
            position.viewToRoom(selectionId)
        }
        return positionIngredientDAO.insert(positionRoom)
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
        ingredientInRecipeDAO.update(IngredientInRecipeRoom(
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
