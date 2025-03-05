package week.on.a.plate.app

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.example.getStartIngredients
import week.on.a.plate.data.dataView.example.getTags
import week.on.a.plate.data.dataView.week.getStdCategoriesSelection
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRepository
import week.on.a.plate.data.repository.tables.filters.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.RecipeTagCategoryRepository
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.startCategoryName
import week.on.a.plate.data.repository.tables.menu.category_selection.CategorySelectionDAO
import week.on.a.plate.data.repository.tables.menu.category_selection.CategorySelectionRoom
import javax.inject.Inject


class FirstStartSettingsUseCase @Inject constructor(
    private val tagCategoryRepository: RecipeTagCategoryRepository,
    private val ingredientRepository: IngredientRepository,
    private val ingredientCategoryRepository: IngredientCategoryRepository,
    private val tagRepository: RecipeTagRepository,
    private val categorySelectionDAO: CategorySelectionDAO
) {

    //todo отчистить при завершении
    val scope = CoroutineScope(Dispatchers.IO)

    fun setStartValue(context: Context) {
        scope.launch {
            val b = tagCategoryRepository.isStartCategoryInstalled()
            if (!b) {
                val startCategory = context.getString(startCategoryName)

                tagCategoryRepository.create(startCategory)
                ingredientCategoryRepository.create(startCategory)

                val tags = getTags(context)
                tags.forEach { category ->
                    val catId = tagCategoryRepository.create(category.name)
                    category.tags.forEach { tag ->
                        tagRepository.insert(tag.tagName, catId)
                    }
                }

                getStdCategoriesSelection().forEach {
                    categorySelectionDAO.insert(
                        CategorySelectionRoom(
                            context.getString(it.fullName),
                            it.stdTime
                        )
                    )
                }

                val ingredients = getStartIngredients(context)
                ingredients.forEach { category ->
                    val catId = ingredientCategoryRepository.create(category.name)
                    category.ingredientViews.forEach { ingredientView ->
                        ingredientRepository.insert(
                            catId,
                            ingredientView.img,
                            ingredientView.name,
                            ingredientView.measure,
                        )
                    }
                }
            }

        }
    }

}