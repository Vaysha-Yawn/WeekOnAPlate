package week.on.a.plate.screens.additional.createRecipe.logic.useCase

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.dialogs.timePick.logic.TimePickViewModel
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import java.time.LocalTime

class RecipeCreateTimeUseCase(
    val mainViewModel: MainViewModel,
    val state: RecipeCreateUIState
) {
    private fun getTime(title: Int, use: (Long) -> Unit) {
        mainViewModel.viewModelScope.launch {
            TimePickViewModel.launch(mainViewModel, title, use)
        }
    }
    fun editRecipeDuration() {
        getTime(R.string.recipe_duration) { time ->
            state.duration.value = LocalTime.ofSecondOfDay(time)
        }
    }

    fun editTimer(event: week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditTimer) {
        getTime(R.string.set_timer) { time ->
            event.recipeStepState.timer.longValue = time
        }
    }

    fun clearTimer(event: week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.ClearTimer) {
        event.recipeStepState.timer.longValue = 0
    }
}