package week.on.a.plate.screens.additional.recipeDetails.logic

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    suspend operator fun invoke(
        context: Context,
        mainViewModel: MainViewModel,
        state: RecipeDetailsState,
    ) {
        val vm = mainViewModel.deleteApplyViewModel
        val mes = context.getString(R.string.delete_alert)
        mainViewModel.nav.navigate(DeleteApplyDestination)
        vm.launchAndGet(context, message = mes) { event ->
            if (event == DeleteApplyEvent.Apply) {
                recipeRepository.delete(state.recipe.id)
                mainViewModel.onEvent(MainEvent.NavigateBack)
            }
        }
    }
}