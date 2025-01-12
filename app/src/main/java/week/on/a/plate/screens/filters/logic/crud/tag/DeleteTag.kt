package week.on.a.plate.screens.filters.logic.crud.tag

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagRepository
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.deleteApply.navigation.DeleteApplyDestination
import javax.inject.Inject

class DeleteTag @Inject constructor(private val recipeTagRepository: RecipeTagRepository) {
    suspend operator fun invoke(
        tag: RecipeTagView, context: Context,
        mainViewModel: MainViewModel
    ) {
        val vmDel = mainViewModel.deleteApplyViewModel
        val mes = context.getString(R.string.delete_tag)
        mainViewModel.nav.navigate(DeleteApplyDestination)
        vmDel.launchAndGet(context, message = mes) { event ->
            if (event == DeleteApplyEvent.Apply) {
                recipeTagRepository.delete(tag.id)
            }
        }
    }
}