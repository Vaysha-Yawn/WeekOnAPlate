package week.on.a.plate.screens.filters.logic.crud.ingredientCategory

import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.repository.tables.filters.ingredientCategory.IngredientCategoryRepository
import javax.inject.Inject

class DeleteIngredientCategory @Inject constructor(private val ingredientCategoryRepository: IngredientCategoryRepository) {
    suspend operator fun invoke(oldCategory: IngredientCategoryView) {
        ingredientCategoryRepository.delete(oldCategory.id)
    }
}