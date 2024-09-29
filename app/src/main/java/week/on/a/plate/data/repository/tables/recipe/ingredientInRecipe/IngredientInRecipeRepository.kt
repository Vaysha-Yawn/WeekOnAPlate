package week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe

import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRepository
import javax.inject.Singleton

@Singleton
class IngredientInRecipeRepository(private val ingredientRepository: IngredientRepository, private val dao: IngredientInRecipeDAO) {

    private val ingredientMapper = IngredientInRecipeMapper()

    suspend fun getIngredients(recipeId:Long): List<IngredientInRecipeView> {
        return  dao.getRecipeAndIngredientInRecipe(recipeId).ingredientInRecipeRoom.map {
            val ingredient = ingredientRepository.getById(it.ingredientId)!!
            with(ingredientMapper) {
                it.roomToView(ingredient)
            }
        }
    }
    suspend fun insertIngredients(list: List<IngredientInRecipeView>, recipeId:Long){
        val ingredients = list.map {  with(ingredientMapper){ it.viewToRoom(recipeId, it.ingredientView.ingredientId)} }
        ingredients.forEach { dao.insert(it) }
    }

    suspend fun deleteByRecipeId(recipeId:Long){
        dao.deleteByRecipeId(recipeId)
    }

    suspend fun deleteIngredients(list: List<IngredientInRecipeView>){
        list.forEach { dao.deleteById(it.id) }
    }
}