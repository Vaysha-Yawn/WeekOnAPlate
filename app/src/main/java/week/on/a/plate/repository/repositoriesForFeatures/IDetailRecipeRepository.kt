package week.on.a.plate.repository.repositoriesForFeatures

import week.on.a.plate.core.data.recipe.RecipeView

interface IDetailRecipeRepository {
    suspend fun getRecipe(recipeId:Long):RecipeView
    suspend fun deleteRecipe(recipeId:Long)
    suspend fun updateRecipe(recipe: RecipeView)
    suspend fun insertRecipe(recipe: RecipeView)
}