package week.on.a.plate.screens.additional.filters.logic.tag

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.repository.room.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import javax.inject.Inject

//used in FilterViewModel
class DeleteTag @Inject constructor(private val recipeTagRepository: RecipeTagRepository) {
    operator fun invoke(
        context: Context,
        onEvent: (MainEvent) -> Unit
    ) {
        val mes = context.getString(R.string.delete_tag)
        onEvent(MainEvent.Navigate(DeleteApplyDestination(null, mes)))
    }

    suspend fun doAfterApplyDelete(tag: RecipeTagView) {
        recipeTagRepository.delete(tag.id)
    }
}