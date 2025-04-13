package week.on.a.plate.screens.additional.filters.logic.ingredientCategory

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.repository.room.filters.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import javax.inject.Inject

class EditIngredientCategory @Inject constructor(private val ingredientCategoryRepository: IngredientCategoryRepository) {
    suspend operator fun invoke(
        oldIngredientCat: IngredientCategoryView,
        dialogOpenParams: MutableState<DialogOpenParams?>,
    ) = coroutineScope {
        val params = EditOneStringViewModel.EditOneStringDialogParams(
            EditOneStringUIState(
                oldIngredientCat.name,
                R.string.edit_category_name,
                R.string.enter_category_name,
            )
        ) { newName ->
            launch {
                ingredientCategoryRepository.updateName(newName, oldIngredientCat.id)
            }
        }
        dialogOpenParams.value = params
    }
}