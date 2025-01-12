package week.on.a.plate.screens.filters.logic.crud.ingredient

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRepository
import week.on.a.plate.dialogs.editOrCreateIngredient.logic.AddIngredientViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.filters.event.FilterEvent
import javax.inject.Inject

class EditIngredient @Inject constructor(private val ingredientRepository: IngredientRepository) {
    suspend operator fun invoke(
        context: Context,
        ingredient: IngredientView,
        mainViewModel: MainViewModel,
        scope: CoroutineScope,
        allIngredients: List<IngredientCategoryView>
    ) {
        val oldCategory = allIngredients.find { it.ingredientViews.contains(ingredient) }!!
        AddIngredientViewModel.launch(
            context,
            ingredient,
            oldCategory,
            oldCategory,
            mainViewModel
        ) { newIngredientAndCategory ->
            scope.launch {
                editIngredientDB(
                    ingredient,
                    newIngredientAndCategory.first,
                    newIngredientAndCategory.second
                )
                mainViewModel.filterViewModel.onEvent(FilterEvent.SearchFilter())
            }
        }
    }

    private suspend fun editIngredientDB(
        ingredientOld: IngredientView,
        ingredientNew: IngredientView,
        newCategory: IngredientCategoryView
    ) {
        ingredientRepository.update(
            ingredientOld.ingredientId,
            newCategory.id,
            ingredientNew.img,
            ingredientNew.name,
            ingredientNew.measure
        )
    }
}