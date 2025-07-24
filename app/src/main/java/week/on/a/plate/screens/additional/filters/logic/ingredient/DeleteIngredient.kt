package week.on.a.plate.screens.additional.filters.logic.ingredient

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.repository.room.filters.ingredient.IngredientRepository
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import javax.inject.Inject

//use in FilterViewModel
class DeleteIngredient @Inject constructor(private val ingredientRepository: IngredientRepository) {

    operator fun invoke(
        context: Context,
        onEvent: (MainEvent) -> Unit
    ) {
        val mes = context.getString(R.string.delete_ingredient)
        onEvent(MainEvent.Navigate(DeleteApplyDestination(null, mes)))
    }

    suspend fun doAfterApplyDelete(ingredient: IngredientView) {
        ingredientRepository.delete(ingredient.ingredientId)
    }

}