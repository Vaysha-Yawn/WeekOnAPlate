package week.on.a.plate.screens.additional.filters.logic.ingredientCategory

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.repository.room.filters.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import javax.inject.Inject

class CreateIngredientCategory @Inject constructor(
    private val ingredientCategoryRepository: IngredientCategoryRepository
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
                    insertNewIngredientCategoryInDB(name, onEvent)
                }
            }
        }
    }

    private suspend fun insertNewIngredientCategoryInDB(name: String, onEvent:(FilterEvent)->Unit) {
        val id = ingredientCategoryRepository.create(name)
        val ingredientCategory = ingredientCategoryRepository.getById(id)
        if (ingredientCategory != null) onEvent(
            FilterEvent.SelectIngredientCategory(
                ingredientCategory
            )
        )
    }


}