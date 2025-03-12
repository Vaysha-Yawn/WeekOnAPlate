package week.on.a.plate.screens.additional.filters.logic.tagCategory

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.repository.room.filters.recipeTagCategory.RecipeTagCategoryRepository
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import javax.inject.Inject

class CreateTagCategory @Inject constructor(
    private val recipeTagCategoryRepository: RecipeTagCategoryRepository
) {

    operator fun invoke(
        onEvent: (FilterEvent) -> Unit,
        scope: CoroutineScope,
        searchText: String,
        mainViewModel: MainViewModel
    ) {
        scope.launch {
            EditOneStringViewModel.launch(
                mainViewModel, EditOneStringUIState(
                    searchText,
                    R.string.add_category,
                    R.string.enter_category_name,
                )
            ) { name ->
                scope.launch {
                    insertNewTagCategoryInDB(name, onEvent)
                }
            }
        }
    }

    private suspend fun insertNewTagCategoryInDB(name: String, onEvent:(FilterEvent)->Unit) {
        val id = recipeTagCategoryRepository.create(name)
        val tagCategoryView = recipeTagCategoryRepository.getById(id)
        if (tagCategoryView != null) onEvent(FilterEvent.SelectTagCategory(tagCategoryView))
    }

}