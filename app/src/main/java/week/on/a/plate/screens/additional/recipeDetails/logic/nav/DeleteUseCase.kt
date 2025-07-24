package week.on.a.plate.screens.additional.recipeDetails.logic.nav

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.additional.recipeDetails.logic.dataLogic.DeleteUseCaseDB
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import javax.inject.Inject

// used in RecipeDetailsViewModel
class DeleteUseCase @Inject constructor(
    private val deleteUseCaseDB: DeleteUseCaseDB
) {
    operator fun invoke(
        context: Context,
        onEvent: (MainEvent) -> Unit,
    ) {
        val mes = context.getString(R.string.delete_alert)
        onEvent(MainEvent.Navigate(DeleteApplyDestination(null, mes)))
    }

    suspend fun doAfterApplyDelete(state: RecipeDetailsState, onEvent: (MainEvent) -> Unit) {
        deleteUseCaseDB(state)
        onEvent(MainEvent.NavigateBack)
    }
}