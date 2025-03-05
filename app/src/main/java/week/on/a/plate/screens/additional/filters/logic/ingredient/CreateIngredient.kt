package week.on.a.plate.screens.additional.filters.logic.ingredient

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRepository
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateIngredient.logic.AddIngredientViewModel
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import javax.inject.Inject

class CreateIngredient @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {
    operator fun invoke(
        context: Context,
        scope: CoroutineScope,
        mainViewModel: MainViewModel,
        searchText: String,

        allIngredients : List<IngredientCategoryView>,
        onEvent: (FilterEvent) -> Unit,
    ) {
        scope.launch {
            val oldIngredient = IngredientView(
                0,
                img = "",
                name = searchText,
                measure = ""
            )
            val defCategoryView =
                allIngredients.find { it.id == 1L }!!
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