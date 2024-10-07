package week.on.a.plate.data.repository.tables.shoppingList


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.data.dataView.ShoppingItemView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientMapper
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeDAO
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeMapper
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeRoom
import javax.inject.Inject


class ShoppingItemRepository @Inject constructor(
    private val shoppingItemDAO: ShoppingItemDAO,
    private val ingredientInRecipeDAO: IngredientInRecipeDAO,
) {
    private val shoppingItemMapper = ShoppingItemMapper()
    private val ingredientInRecipeMapper = IngredientInRecipeMapper()
    private val ingredientMapper = IngredientMapper()

    fun getCheckedFlow(): Flow<List<ShoppingItemView>> {
        return shoppingItemDAO.getCheckedFlow().map { list ->
            list.map { shoppingItemRoom ->
                val ingredient =
                    ingredientInRecipeDAO.getIngredientAndIngredientInRecipe(shoppingItemRoom.ingredientInRecipeId)

                val ingredientView =
                    with(ingredientMapper) { ingredient.ingredientRoom.roomToView() }

                val ingredientInMenuView = with(ingredientInRecipeMapper) {
                    ingredient.ingredientInRecipeRoom.roomToView(ingredientView)
                }

                with(shoppingItemMapper) {
                    shoppingItemRoom.roomToView(ingredientInRecipe = ingredientInMenuView)
                }
            }
        }
    }

    fun getUnCheckedFlow(): Flow<List<ShoppingItemView>> {
        return shoppingItemDAO.getUnCheckedFlow().map { list ->
            list.map { shoppingItemRoom ->
                val ingredient =
                    ingredientInRecipeDAO.getIngredientAndIngredientInRecipe(shoppingItemRoom.ingredientInRecipeId)

                val ingredientView =
                    with(ingredientMapper) { ingredient.ingredientRoom.roomToView() }

                val ingredientInMenuView = with(ingredientInRecipeMapper) {
                    ingredient.ingredientInRecipeRoom.roomToView(ingredientView)
                }

                with(shoppingItemMapper) {
                    shoppingItemRoom.roomToView(ingredientInRecipe = ingredientInMenuView)
                }
            }
        }
    }

    suspend fun getAll(): List<ShoppingItemView> {
        return shoppingItemDAO.getAll().map { position ->
            val ingredient =
                ingredientInRecipeDAO.getIngredientAndIngredientInRecipe(position.ingredientInRecipeId)

            val ingredientView =
                with(ingredientMapper) { ingredient.ingredientRoom.roomToView() }

            val ingredientInMenuView = with(ingredientInRecipeMapper) {
                ingredient.ingredientInRecipeRoom.roomToView(ingredientView)
            }

            with(shoppingItemMapper) {
                position.roomToView(ingredientInRecipe = ingredientInMenuView)
            }
        }
    }


    suspend fun insert(itemView: ShoppingItemView) {
        val ingredientInRecipeRoom = with(IngredientInRecipeMapper()) {
            itemView.ingredientInRecipe.viewToRoom(
                0
            )
        }

        val newIngredientInRecipeId = ingredientInRecipeDAO.insert(ingredientInRecipeRoom)

        val shoppingItemRoom = with(shoppingItemMapper) {
            itemView.viewToRoom(newIngredientInRecipeId)
        }

        shoppingItemDAO.insert(shoppingItemRoom)
    }

    suspend fun update(
        id: Long,
        ingredientInRecipeId: Long,
        checked: Boolean,
        ingredientId: Long,
        description: String,
        count: Double
    ) {
        shoppingItemDAO.update(
            ShoppingItemRoom(ingredientInRecipeId, checked).apply {
                this.id = id
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
        shoppingItemDAO.deleteById(id)
    }

    suspend fun deleteAllChecked() {
        shoppingItemDAO.deleteAllChecked()
    }

}
