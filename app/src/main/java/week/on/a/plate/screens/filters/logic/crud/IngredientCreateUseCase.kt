package week.on.a.plate.screens.filters.logic.crud

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRepository
import week.on.a.plate.dialogs.editOrCreateIngredient.logic.AddIngredientViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.filters.event.FilterEvent
import javax.inject.Inject

class IngredientCreateUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {
    private fun toCreateIngredient(
        context: Context,
        onEvent: (FilterEvent) -> Unit,
        scope: CoroutineScope,
        searchText: String,
        mainViewModel: MainViewModel,
        allIngredients : List<IngredientCategoryView>
    ) {
        scope.launch {
            val oldIngredient = IngredientView(
                0,
                img = "",
                name = searchText,
                measure = ""
            )
            val defCategoryView =
                allIngredients.find { it.name == context.getString(R.string.no_category) }!!
            AddIngredientViewModel.launch(
                context,
                oldIngredient,
                null,
                defCategoryView,
                mainViewModel
            ) { ingredientData ->
                scope.launch {
                    insertNewIngredientInDB(ingredientData, onEvent)
                    mainViewModel.filterViewModel.onEvent(FilterEvent.SearchFilter())
                }
            }
        }
    }

    private suspend fun insertNewIngredientInDB(
        ingredientData: Pair<IngredientView, IngredientCategoryView>,
        onEvent: (FilterEvent) -> Unit,
    ) {
        val newIngredientId = ingredientRepository.insert(
            categoryId = ingredientData.second.id,
            img = ingredientData.first.img,
            name = ingredientData.first.name,
            measure = ingredientData.first.measure
        )
        val newIngredient = ingredientRepository.getById(newIngredientId)
        if (newIngredient != null) onEvent(FilterEvent.SelectIngredient(newIngredient))
    }


}