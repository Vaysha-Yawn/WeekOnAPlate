package week.on.a.plate.screens.additional.createRecipe.logic.useCase

import androidx.compose.runtime.MutableState
import week.on.a.plate.R
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.dialogs.timePick.logic.TimePickViewModel
import week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import java.time.LocalTime
import javax.inject.Inject

class RecipeCreateTimeUseCase @Inject constructor() {
    private fun getTime(
        dialogOpenParams: MutableState<DialogOpenParams?>,
        title: Int,
        use: (Long) -> Unit
    ) {
        val params = TimePickViewModel.TimePickDialogParams(title, use)
        dialogOpenParams.value = params
    }

    fun editRecipeDuration(
        dialogOpenParams: MutableState<DialogOpenParams?>,
        state: RecipeCreateUIState
    ) {
        getTime(dialogOpenParams, R.string.recipe_duration) { time ->
            state.duration.value = LocalTime.ofSecondOfDay(time)
        }
    }

    fun editTimer(
        dialogOpenParams: MutableState<DialogOpenParams?>,
        event: RecipeCreateEvent.EditTimer
    ) {
        getTime(dialogOpenParams, R.string.set_timer) { time ->
            event.recipeStepState.timer.longValue = time
        }
    }

    fun clearTimer(event: RecipeCreateEvent.ClearTimer) {
        event.recipeStepState.timer.longValue = 0
    }
}