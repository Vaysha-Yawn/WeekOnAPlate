package week.on.a.plate.screens.additional.filters.logic.ingredientCategory

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.repository.tables.filters.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import javax.inject.Inject

class EditIngredientCategory @Inject constructor(private val ingredientCategoryRepository: IngredientCategoryRepository) {
    suspend operator fun invoke(
        oldIngredientCat: IngredientCategoryView,
        mainViewModel: MainViewModel,
        scope: CoroutineScope
    ) {
        EditOneStringViewModel.launch(
            mainViewModel, EditOneStringUIState(
                oldIngredientCat.name,
                R.string.edit_category_name,
                R.string.enter_category_name,
            )
        ) { newName ->
            scope.launch {
                ingredientCategoryRepository.updateName(newName, oldIngredientCat.id)
            }
        }
    }
}