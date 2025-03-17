package week.on.a.plate.screens.additional.recipeDetails.logic.nav

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.additional.recipeDetails.logic.dataLogic.DeleteUseCaseDB
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val deleteUseCaseDB: DeleteUseCaseDB
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
                deleteUseCaseDB.invoke(state)
                mainViewModel.onEvent(MainEvent.NavigateBack)
            }
        }
    }
}