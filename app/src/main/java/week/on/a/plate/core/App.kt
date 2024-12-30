package week.on.a.plate.core

import android.app.Application
import com.yandex.mobile.ads.common.MobileAds
import dagger.hilt.android.HiltAndroidApp
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

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var tagCategoryRepository: RecipeTagCategoryRepository
    @Inject
    lateinit var ingredientRepository: IngredientRepository
    @Inject
    lateinit var ingredientCategoryRepository: IngredientCategoryRepository
    @Inject
    lateinit var tagRepository: RecipeTagRepository
    @Inject
    lateinit var categorySelectionDAO: CategorySelectionDAO

    override fun onCreate() {
        super.onCreate()
        setStartValue()
    }

    private fun setStartValue() {
        CoroutineScope(Dispatchers.IO).launch {
            val b = tagCategoryRepository.isStartCategoryInstalled()
            if (!b) {
                val startCategory = this@App.getString(startCategoryName)
                tagCategoryRepository.create(startCategory)
                ingredientCategoryRepository.create(startCategory)
                val tags = getTags(this@App)
                tags.forEach { category ->
                    val catId = tagCategoryRepository.create(category.name)
                    category.tags.forEach { tag ->
                        tagRepository.insert(tag.tagName, catId)
                    }
                }
                val ingredients = getStartIngredients(this@App)
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

                getStdCategoriesSelection().forEach {
                    categorySelectionDAO.insert(CategorySelectionRoom(this@App.getString(it.fullName) , it.stdTime))
                }
            }
        }
    }
}