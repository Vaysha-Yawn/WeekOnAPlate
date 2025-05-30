package week.on.a.plate.screens.additional.createRecipe.logic.useCase

import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.R
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.dialogs.timePick.logic.TimePickViewModel
import week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import java.time.LocalTime
import javax.inject.Inject

class RecipeCreateTimeUseCase @Inject constructor() {
    private suspend fun getTime(
        dialogOpenParams: MutableStateFlow<DialogOpenParams?>,
        title: Int,
        use: (Long) -> Unit
    ) {
        val params = TimePickViewModel.TimePickDialogParams(title, use)
        dialogOpenParams.emit(params)
    }

    suspend fun editRecipeDuration(
        dialogOpenParams: MutableStateFlow<DialogOpenParams?>,
        state: RecipeCreateUIState
    ) {
        getTime(dialogOpenParams, R.string.recipe_duration) { time ->
            state.duration.value = LocalTime.ofSecondOfDay(time)
        }
    }

    suspend fun editTimer(
        dialogOpenParams: MutableStateFlow<DialogOpenParams?>,
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