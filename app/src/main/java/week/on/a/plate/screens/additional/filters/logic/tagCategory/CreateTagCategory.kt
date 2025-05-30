package week.on.a.plate.screens.additional.filters.logic.tagCategory

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.repository.room.filters.recipeTagCategory.RecipeTagCategoryRepository
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import javax.inject.Inject

class CreateTagCategory @Inject constructor(
    private val recipeTagCategoryRepository: RecipeTagCategoryRepository
) {
    suspend operator fun invoke(
        onEvent: (FilterEvent) -> Unit,
        searchText: String,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
    ) = coroutineScope {
        val params = EditOneStringViewModel.EditOneStringDialogParams(
            EditOneStringUIState(
                searchText,
                R.string.add_category,
                R.string.enter_category_name,
            )
        ) { name ->
            scope.launch(Dispatchers.IO) {
                insertNewTagCategoryInDB(name, onEvent)
            }
        }
        dialogOpenParams.value = params
    }

    private suspend fun insertNewTagCategoryInDB(name: String, onEvent: (FilterEvent) -> Unit) {
        val id = recipeTagCategoryRepository.create(name)
        val tagCategoryView = recipeTagCategoryRepository.getById(id)
        if (tagCategoryView != null) onEvent(FilterEvent.SelectTagCategory(tagCategoryView))
    }

}