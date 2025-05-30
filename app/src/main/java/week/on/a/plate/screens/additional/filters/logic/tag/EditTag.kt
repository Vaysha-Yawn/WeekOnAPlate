package week.on.a.plate.screens.additional.filters.logic.tag

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.room.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateTag.logic.AddTagViewModel
import javax.inject.Inject

class EditTag @Inject constructor(private val recipeTagRepository: RecipeTagRepository) {
    suspend operator fun invoke(
        tag: RecipeTagView, dialogOpenParams: MutableState<DialogOpenParams?>,
        allTags: List<TagCategoryView>,
        scope: CoroutineScope,
    ) = coroutineScope {
        val oldCategory = allTags.find { it.tags.contains(tag) }
        val params = AddTagViewModel.AddTagDialogNavParams(
            tag.tagName, oldCategory, oldCategory!!
        ) { newNameAndCategory ->
            scope.launch(Dispatchers.IO) {
                recipeTagRepository.update(
                    tag.id,
                    newNameAndCategory.first,
                    newNameAndCategory.second.id
                )
            }
        }
        dialogOpenParams.value = params
    }
}