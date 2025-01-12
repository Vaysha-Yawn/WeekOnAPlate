package week.on.a.plate.screens.filters.logic.crud.tag

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.dialogs.editOrCreateTag.logic.AddTagViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import javax.inject.Inject

class EditTag @Inject constructor(private val recipeTagRepository: RecipeTagRepository) {
    suspend operator fun invoke(
        tag: RecipeTagView, mainViewModel: MainViewModel,
        scope: CoroutineScope, allTags: List<TagCategoryView>
    ) {
        val oldCategory = allTags.find { it.tags.contains(tag) }
        AddTagViewModel.launch(
            tag.tagName, oldCategory, oldCategory!!, mainViewModel
        ) { newNameAndCategory ->
            scope.launch {
                recipeTagRepository.update(
                    tag.id,
                    newNameAndCategory.first,
                    newNameAndCategory.second.id
                )
            }
        }
    }
}