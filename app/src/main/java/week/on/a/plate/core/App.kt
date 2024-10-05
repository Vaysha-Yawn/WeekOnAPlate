package week.on.a.plate.core

import android.app.Application
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.example.ingredients
import week.on.a.plate.data.dataView.example.tags
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRepository
import week.on.a.plate.data.repository.tables.filters.ingredientCategory.IngredientCategoryRepository
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.RecipeTagCategoryRepository
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltAndroidApp
class App:Application(){

    @Inject lateinit var tagCategoryRepository: RecipeTagCategoryRepository
    @Inject lateinit var ingredientRepository: IngredientRepository
    @Inject lateinit var ingredientCategoryRepository: IngredientCategoryRepository
    @Inject lateinit var tagRepository: RecipeTagRepository

    override fun onCreate() {
        super.onCreate()
        setStartValue()
    }

    private fun setStartValue() {
        CoroutineScope(Dispatchers.IO).launch {
            val b = tagCategoryRepository.isStartCategoryInstalled()
            if (!b){
                tags.forEach { category ->
                    val catId = tagCategoryRepository.create(category.name)
                    category.tags.forEach { tag ->
                        tagRepository.insert(tag.tagName, catId)
                    }
                }
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