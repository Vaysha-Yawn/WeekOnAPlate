package week.on.a.plate.screens.additional.filters.logic.tag

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.repository.room.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyNavParams
import javax.inject.Inject

class DeleteTag @Inject constructor(private val recipeTagRepository: RecipeTagRepository) {
    suspend operator fun invoke(
        tag: RecipeTagView, context: Context,
        onEvent: (MainEvent) -> Unit
    ) {
        val mes = context.getString(R.string.delete_tag)
        val params = DeleteApplyNavParams(context, message = mes) { event ->
            if (event == DeleteApplyEvent.Apply) {
                recipeTagRepository.delete(tag.id)
            }
        }
        onEvent(MainEvent.Navigate(DeleteApplyDestination, params))
    }
}