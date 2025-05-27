package week.on.a.plate.screens.additional.filters.logic.tag

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.room.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateTag.logic.AddTagViewModel
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import javax.inject.Inject

class CreateTag @Inject constructor(
    private val recipeTagRepository: RecipeTagRepository
) {
    operator fun invoke(
        onEvent: (FilterEvent) -> Unit,
        scope: CoroutineScope,
        searchText: String,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        allTags: List<TagCategoryView>
    ) {
        scope.launch {
            val defCategoryView =
                allTags.find { it.id == 1L }!!
            dialogOpenParams.value = AddTagViewModel.AddTagDialogNavParams(
                searchText,
                null,
                defCategoryView
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