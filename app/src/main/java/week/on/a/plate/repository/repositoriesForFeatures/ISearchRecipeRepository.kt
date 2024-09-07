package week.on.a.plate.repository.repositoriesForFeatures

import kotlinx.coroutines.flow.Flow
import week.on.a.plate.core.data.recipe.IngredientCategoryView
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.recipe.RecipeView
import week.on.a.plate.core.data.recipe.TagCategoryView

interface ISearchRecipeRepository {
    suspend fun getRecipeTagCategory():Flow<List<TagCategoryView>>
    suspend fun getIngredientCategory():Flow<List<IngredientCategoryView>>
    suspend fun searchRecipes(tags:List<RecipeTagView>, ingredients:List<IngredientView>, searchQuery:String):Flow<List<RecipeView>>
    suspend fun get20Random()
}