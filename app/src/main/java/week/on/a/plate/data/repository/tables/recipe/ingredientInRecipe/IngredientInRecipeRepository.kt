package week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IngredientInRecipeRepository @Inject constructor(
    private val ingredientRepository: IngredientRepository,
    private val dao: IngredientInRecipeDAO
) {

    private val ingredientMapper = IngredientInRecipeMapper()

    suspend fun getIngredients(recipeId: Long): List<IngredientInRecipeView> {
        return dao.getRecipeAndIngredientInRecipe(recipeId).ingredientInRecipeRoom.map { ingredientInRecipeRoom ->
            val ingredient = ingredientRepository.getById(ingredientInRecipeRoom.ingredientId)!!
            with(ingredientMapper) {
                ingredientInRecipeRoom.roomToView(ingredient)
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getIngredientsFlow(recipeId: Long): Flow<List<IngredientInRecipeView>> {
        return dao.getRecipeAndIngredientInRecipeFlow(recipeId)
            .flatMapLatest { recipeAndIngredientInRecipe ->
                val ingredientFlows =
                    recipeAndIngredientInRecipe.ingredientInRecipeRoom.map { ingredientInRecipeRoom ->
                        ingredientRepository.getByIdFlow(ingredientInRecipeRoom.ingredientId)
                            .map { ingredientView ->
                                with(ingredientMapper) {
                                    ingredientInRecipeRoom.roomToView(ingredientView!!)
                                }
                            }
                    }
                combine(ingredientFlows) { it.toList() }
            }
    }


    suspend fun insertIngredient(ingredientInRecipe: IngredientInRecipeView, recipeId: Long) {
        val ingredientRoom =
            with(ingredientMapper) {
                ingredientInRecipe.viewToRoom(
                    recipeId,
                )
            }
        dao.insert(ingredientRoom)
    }

    suspend fun deleteByRecipeId(recipeId: Long) {
        dao.deleteByRecipeId(recipeId)
    }

    suspend fun deleteIngredients(ingredientInRecipe: IngredientInRecipeView) {
        dao.deleteById(ingredientInRecipe.id)
    }

    suspend fun update(ingredient: IngredientInRecipeView, recipeId: Long) {
        val ingredientRoom =
            with(ingredientMapper) {
                ingredient.viewToRoom(
                    recipeId,
                )
            }.apply { this.id = ingredient.id }
       dao.update(ingredientRoom)
    }
}