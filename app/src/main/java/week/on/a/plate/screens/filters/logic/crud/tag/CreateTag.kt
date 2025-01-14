package week.on.a.plate.screens.filters.logic.crud.tag

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.dialogs.editOrCreateTag.logic.AddTagViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.filters.event.FilterEvent
import javax.inject.Inject

class CreateTag @Inject constructor(
    private val recipeTagRepository: RecipeTagRepository
) {
    operator fun invoke(
        context: Context,
        onEvent: (FilterEvent) -> Unit,
        scope: CoroutineScope,
        searchText: String,
        mainViewModel: MainViewModel,
        allTags: List<TagCategoryView>
    ) {
        scope.launch {
            val defCategoryView =
                allTags.find { it.id == 1L }!!
            AddTagViewModel.launch(
                searchText,
                null,
                defCategoryView,
                mainViewModel
            ) { tagData ->
                scope.launch {
                    insertNewTagInDB(tagData, onEvent)
                }
            }
        }
    }

    private suspend fun insertNewTagInDB(
        tagData: Pair<String, TagCategoryView>,
        onEvent: (FilterEvent) -> Unit,
    ) {
        val newTagId = recipeTagRepository.insert(tagData.first, tagData.second.id)
        val newTag = recipeTagRepository.getById(newTagId)
        if (newTag != null) onEvent(FilterEvent.SelectTag(newTag))
    }
}