package week.on.a.plate.screens.filters.logic

import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterResult
import week.on.a.plate.screens.filters.state.FilterUIState
import javax.inject.Inject

class SearchUseCase @Inject constructor(){

    suspend fun search(
        text: String,
        allTagsCategories: List<TagCategoryView>,
        allIngredientsCategories: List<IngredientCategoryView>,
        state:FilterUIState
    ) {
        val filterEnum = state.filterEnum.value
        if (filterEnum == FilterEnum.Tag || filterEnum == FilterEnum.IngredientAndTag) state.resultSearchTags.value =
            searchTags(text, allTagsCategories)
        if (filterEnum == FilterEnum.Ingredient || filterEnum == FilterEnum.IngredientAndTag) state.resultSearchIngredients.value =
            searchIngredients(text, allIngredientsCategories)
        if (filterEnum == FilterEnum.CategoryTag) state.resultSearchTagsCategories.value =
            searchTagsCategories(text, allTagsCategories)
        if (filterEnum == FilterEnum.CategoryIngredient) state.resultSearchIngredientsCategories.value =
            searchIngredientCategories(text, allIngredientsCategories)

    }

    fun getAllSearch(
        name: String,
        currentFilterEnum: FilterEnum,
        allTagsCategories: List<TagCategoryView>,
        allIngredientsCategories: List<IngredientCategoryView>
    ): FilterResult {
        val tags =
            if (currentFilterEnum == FilterEnum.Tag || currentFilterEnum == FilterEnum.IngredientAndTag) searchTags(
                name, allTagsCategories
            ) else null
        val ingredients =
            if (currentFilterEnum == FilterEnum.Ingredient || currentFilterEnum == FilterEnum.IngredientAndTag) searchIngredients(
                name, allIngredientsCategories
            ) else null
        val tagsCategories =
            if (currentFilterEnum == FilterEnum.CategoryTag) searchTagsCategories(
                name,
                allTagsCategories
            ) else null
        val ingredientsCategories =
            if (currentFilterEnum == FilterEnum.CategoryIngredient) searchIngredientCategories(
                name, allIngredientsCategories
            ) else null

        return FilterResult(tags, ingredients, tagsCategories, ingredientsCategories)
    }

    private fun searchTagsCategories(
        text: String,
        allTagsCategories: List<TagCategoryView>
    ): List<TagCategoryView> {
        return searchT(
            allTagsCategories,
        ) { tagCat ->
            tagCat.name.contains(text.trim(), true)
        }
    }

    private fun searchIngredientCategories(
        text: String,
        allIngredientsCategories: List<IngredientCategoryView>
    ): List<IngredientCategoryView> {
        return searchT(
            allIngredientsCategories,
        ) { ingredientCat ->
            ingredientCat.name.contains(text.trim(), true)
        }
    }

    private fun searchTags(
        text: String,
        allTagsCategories: List<TagCategoryView>
    ): List<RecipeTagView> {
        return searchT(
            allTagsCategories.flatMap { it.tags },
        ) { tag ->
            tag.tagName.contains(text.trim(), true)
        }
    }

    private fun searchIngredients(
        text: String,
        allIngredientsCategories: List<IngredientCategoryView>
    ): List<IngredientView> {
        return searchT(
            allIngredientsCategories.flatMap { it.ingredientViews },
        ) { ingredient ->
            ingredient.name.contains(text.trim(), true)
        }
    }

    private fun <T> searchT(
        listAll: List<T>,
        filter: (T) -> Boolean
    ): List<T> {
        return listAll.filter { filter(it) }
    }
}