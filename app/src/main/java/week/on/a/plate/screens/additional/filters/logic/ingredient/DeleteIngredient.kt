package week.on.a.plate.screens.additional.filters.logic.ingredient

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientRepository
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import javax.inject.Inject

class DeleteIngredient @Inject constructor(private val ingredientRepository: IngredientRepository) {
    suspend operator fun invoke(
        ingredient: IngredientView,
        context: Context,
        mainViewModel: MainViewModel
    ) {
        val vmDel = mainViewModel.deleteApplyViewModel
        val mes = context.getString(R.string.delete_ingredient)
        mainViewModel.nav.navigate(DeleteApplyDestination)
        vmDel.launchAndGet(context, message = mes) { event ->
            if (event == DeleteApplyEvent.Apply) {
                ingredientRepository.delete(ingredient.ingredientId)
            }
        }
    }
}