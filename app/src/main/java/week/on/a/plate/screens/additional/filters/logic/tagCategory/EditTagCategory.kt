package week.on.a.plate.screens.additional.filters.logic.tagCategory

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.room.filters.recipeTagCategory.RecipeTagCategoryRepository
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import javax.inject.Inject

class EditTagCategory @Inject constructor(private val recipeTagCategoryRepository: RecipeTagCategoryRepository) {
    suspend operator fun invoke(
        oldTagCat: TagCategoryView, mainViewModel: MainViewModel,
        scope: CoroutineScope
    ) {
        EditOneStringViewModel.launch(
            mainViewModel, EditOneStringUIState(
                oldTagCat.name,
                R.string.edit_category_name,
                R.string.enter_category_name,
            )
        ) { newName ->
            scope.launch {
                recipeTagCategoryRepository.updateName(newName, oldTagCat.id)
            }
        }
    }

}