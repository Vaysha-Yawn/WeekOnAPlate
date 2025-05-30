package week.on.a.plate.screens.additional.filters.logic.ingredientCategory

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.repository.room.filters.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import javax.inject.Inject

class CreateIngredientCategory @Inject constructor(
    private val ingredientCategoryRepository: IngredientCategoryRepository
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
                insertNewIngredientCategoryInDB(name, onEvent)
            }
        }
        dialogOpenParams.value = params
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