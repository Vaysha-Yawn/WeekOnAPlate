package week.on.a.plate.search.data

import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.recipe.TagCategoryView

data class SearchCategories(
    val tagsCategories: List<TagCategoryView>,
    val listFavorites: List<RecipeTagView>,
    val listRecent: List<RecipeTagView>,
    val list20Random: List<RecipeTagView>,
){
    companion object{
        val SearchCategoriesEmpty = SearchCategories(listOf(), listOf(), listOf(), listOf())
    }
}

