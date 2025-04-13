package week.on.a.plate.screens.additional.recipeDetails.logic.nav

import android.content.Context
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.screens.additional.deleteApply.event.DeleteApplyEvent
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyDestination
import week.on.a.plate.screens.additional.deleteApply.navigation.DeleteApplyNavParams
import week.on.a.plate.screens.additional.recipeDetails.logic.dataLogic.DeleteUseCaseDB
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val deleteUseCaseDB: DeleteUseCaseDB
) {
    suspend operator fun invoke(
        context: Context,
        state: RecipeDetailsState,
        onEvent: (MainEvent) -> Unit,
    ) {
        val mes = context.getString(R.string.delete_alert)
        val params = DeleteApplyNavParams(context, message = mes) { event ->
            if (event == DeleteApplyEvent.Apply) {
                deleteUseCaseDB.invoke(state)
                onEvent(MainEvent.NavigateBack)
            }
        }
        onEvent(MainEvent.Navigate(DeleteApplyDestination, params))
    }
}