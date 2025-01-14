package week.on.a.plate.screens.filters.logic

import androidx.compose.runtime.MutableState
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.filters.state.FilterMode
import javax.inject.Inject

class SelectUseCase @Inject constructor(){
    fun selectTag(
        tag: RecipeTagView,
        selectedTags: MutableState<List<RecipeTagView>>,
        filterMode: FilterMode,
        onEvent: (FilterEvent) -> Unit
    ) {
        select(tag, selectedTags, filterMode, onEvent)
    }

    fun selectIngredient(
        ingredient: IngredientView,
        selectedIngredients: MutableState<List<IngredientView>>,
        filterMode: FilterMode,
        onEvent: (FilterEvent) -> Unit
    ) {
        select(ingredient, selectedIngredients, filterMode, onEvent)
    }

    fun selectTagCategory(
        tagCategory: TagCategoryView,
        selectedTagsCategories: MutableState<List<TagCategoryView>>,
        filterMode: FilterMode,
        onEvent: (FilterEvent) -> Unit
    ) {
        select(tagCategory, selectedTagsCategories, filterMode, onEvent)
    }

    fun selectIngredientCategory(
        ingredientCat: IngredientCategoryView,
        selectedIngredientsCategories: MutableState<List<IngredientCategoryView>>,
        filterMode: FilterMode,
        onEvent: (FilterEvent) -> Unit
    ) {
        select(ingredientCat, selectedIngredientsCategories, filterMode, onEvent)
    }


    private fun <T> select(
        t: T,
        list: MutableState<List<T>>,
        filterMode: FilterMode,
        onEvent: (FilterEvent) -> Unit
    ) {
        list.value = list.value.toMutableList().apply {
            if (filterMode == FilterMode.One) {
                add(t)
            } else {
                if (contains(t)) {
                    remove(t)
                } else {
                    add(t)
                }
            }
        }.toList()
        if (filterMode == FilterMode.One) {
            onEvent(FilterEvent.Done)
        }
    }
}