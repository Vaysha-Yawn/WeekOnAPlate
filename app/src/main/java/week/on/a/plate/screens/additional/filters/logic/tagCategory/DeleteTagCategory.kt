package week.on.a.plate.screens.additional.filters.logic.tagCategory

import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.RecipeTagCategoryRepository
import javax.inject.Inject

class DeleteTagCategory @Inject constructor(private val recipeTagCategoryRepository: RecipeTagCategoryRepository) {
    suspend operator fun invoke(oldCategory: TagCategoryView) {
        recipeTagCategoryRepository.delete(oldCategory.id)
    }
}