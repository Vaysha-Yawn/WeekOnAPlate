package week.on.a.plate.screens.additional.filters.logic.ingredient

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.repository.room.filters.ingredient.IngredientRepository
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateIngredient.logic.AddIngredientViewModel
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import javax.inject.Inject

class EditIngredient @Inject constructor(private val ingredientRepository: IngredientRepository) {
    suspend operator fun invoke(
        context: Context,
        ingredient: IngredientView,
        onEvent: (FilterEvent) -> Unit,
        allIngredients: List<IngredientCategoryView>,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
    ) = coroutineScope {
        val oldCategory = allIngredients.find { it.ingredientViews.contains(ingredient) }!!
        val params = AddIngredientViewModel.AddIngredientDialogNavParams(
            context,
            ingredient,
            oldCategory,
            oldCategory
        ) { newIngredientAndCategory ->
            scope.launch(Dispatchers.IO) {
                editIngredientDB(
                    ingredient,
                    newIngredientAndCategory.first,
                    newIngredientAndCategory.second
                )
                onEvent(FilterEvent.SearchFilter())
            }
        }
        dialogOpenParams.value = params
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