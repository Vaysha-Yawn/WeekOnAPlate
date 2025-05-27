package week.on.a.plate.screens.additional.filters.logic.ingredient

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.repository.room.filters.ingredient.IngredientRepository
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateIngredient.logic.AddIngredientViewModel
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import javax.inject.Inject

class CreateIngredient @Inject constructor(
    private val ingredientRepository: IngredientRepository
) {
    operator fun invoke(
        context: Context,
        scope: CoroutineScope,
        dialogOpenParams: MutableState<DialogOpenParams?>,
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
            val params = AddIngredientViewModel.AddIngredientDialogNavParams(
                context,
                oldIngredient,
                null,
                defCategoryView,
            ) { ingredientData ->
                scope.launch {
                    insertNewIngredientInDB(ingredientData, onEvent)
                    onEvent(FilterEvent.SearchFilter())
                }
            }
            dialogOpenParams.value = params
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