package week.on.a.plate.screens.filters.logic

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.filters.dialogs.filterVoiceApply.logic.FilterVoiceApplyViewModel
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.filters.state.FilterEnum
import week.on.a.plate.screens.filters.state.FilterResult
import javax.inject.Inject

class VoiceSearchUseCase @Inject constructor() {
    operator fun invoke(
        context: Context,
        mainViewModel: MainViewModel,
        onEvent: (FilterEvent) -> Unit,
        searchText: MutableState<String>,
        scope: CoroutineScope,
        currentFilterEnum: FilterEnum,
        searchTags: (String) -> List<RecipeTagView>,
        searchIngredients: (String) -> List<IngredientView>,
        searchTagsCategories: (String) -> List<TagCategoryView>,
        searchIngredientCategories: (String) -> List<IngredientCategoryView>,
    ) {
        mainViewModel.onEvent(MainEvent.VoiceToText(context) { strings: ArrayList<String>? ->
            if (strings == null) return@VoiceToText
            scope.launch {
                val searchedList = strings.getOrNull(0)?.split(" ") ?: return@launch

                val listIngredientView = mutableListOf<IngredientView>()
                val listTags = mutableListOf<RecipeTagView>()

                searchedList.forEach {
                    val res = getAllSearch(
                        it,
                        currentFilterEnum,
                        searchTags,
                        searchIngredients,
                        searchTagsCategories,
                        searchIngredientCategories
                    )
                    if (res.tags != null) listTags.addAll(res.tags)
                    if (res.ingredients != null) listIngredientView.addAll(res.ingredients)
                }

                if (listIngredientView.isEmpty() && listTags.isEmpty()) {
                    searchText.value = strings.joinToString()
                    return@launch
                }

                FilterVoiceApplyViewModel.launch(
                    listTags, listIngredientView,
                    mainViewModel
                ) { stateApply ->
                    stateApply.selectedTags.value.forEach {
                        onEvent(FilterEvent.SelectTag(it))
                    }
                    stateApply.selectedIngredients.value.forEach {
                        onEvent(FilterEvent.SelectIngredient(it))
                    }
                }
            }
        })
    }

    private fun getAllSearch(
        name: String,
        currentFilterEnum: FilterEnum,
        searchTags: (String) -> List<RecipeTagView>,
        searchIngredients: (String) -> List<IngredientView>,
        searchTagsCategories: (String) -> List<TagCategoryView>,
        searchIngredientCategories: (String) -> List<IngredientCategoryView>,
    ): FilterResult {
        val tags =
            if (currentFilterEnum == FilterEnum.Tag || currentFilterEnum == FilterEnum.IngredientAndTag) searchTags(
                name
            ) else null
        val ingredients =
            if (currentFilterEnum == FilterEnum.Ingredient || currentFilterEnum == FilterEnum.IngredientAndTag) searchIngredients(
                name
            ) else null
        val tagsCategories =
            if (currentFilterEnum == FilterEnum.CategoryTag) searchTagsCategories(name) else null
        val ingredientsCategories =
            if (currentFilterEnum == FilterEnum.CategoryIngredient) searchIngredientCategories(
                name
            ) else null

        return FilterResult(tags, ingredients, tagsCategories, ingredientsCategories)
    }
}