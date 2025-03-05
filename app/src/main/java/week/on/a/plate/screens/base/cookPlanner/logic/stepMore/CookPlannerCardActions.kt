package week.on.a.plate.screens.base.cookPlanner.logic.stepMore

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.dialogs.cookStepMore.event.CookStepMoreEvent
import week.on.a.plate.dialogs.cookStepMore.logic.CookStepMoreDialogViewModel
import week.on.a.plate.dialogs.timePick.logic.TimePickViewModel
import week.on.a.plate.screens.base.cookPlanner.event.CookPlannerEvent
import week.on.a.plate.screens.base.cookPlanner.logic.stepMore.usecases.UseCaseWrapperCookPlannerCardActions

class CookPlannerCardActions(
    private val mainViewModel: MainViewModel,
    private val scope: CoroutineScope,
    private val useCases: UseCaseWrapperCookPlannerCardActions
    ) {

    fun showStepMore(event: CookPlannerEvent.ShowStepMore) {
        CookStepMoreDialogViewModel.launch(mainViewModel) {
            when (it) {
                CookStepMoreEvent.ChangeEndRecipeTime -> {
                    changeEndRecipeTime(event)
                }
                CookStepMoreEvent.ChangeStartRecipeTime -> changeStartRecipeTime(event.groupView)
                CookStepMoreEvent.Close -> {}
                CookStepMoreEvent.ChangePortionsCount -> changePortionsCount(event.groupView)
                CookStepMoreEvent.Delete -> mainViewModel.viewModelScope.launch {
                    useCases.deleteCookPlannerGroupUseCase(
                        event.groupView
                    )
                }
            }
        }
    }

    private fun changeEndRecipeTime(event: CookPlannerEvent.ShowStepMore) {
        getTime(R.string.when_recipe_end) { time ->
            scope.launch {
                useCases.changeEndRecipeTimeUseCase(event.groupView, time)
            }
        }
    }

    private fun getTime(title: Int, use: (Long) -> Unit) {
        TimePickViewModel.launch(mainViewModel, title, use)
    }

    private fun changePortionsCount(group: CookPlannerGroupView) {
        ChangePortionsCountViewModel.launch(mainViewModel, group.portionsCount) { portionsCount ->
            scope.launch {
                useCases.changePortionsCountUseCase(group, portionsCount)
            }
        }
    }

    private fun changeStartRecipeTime(group: CookPlannerGroupView) {
        getTime(R.string.when_recipe_start) {
            scope.launch {
                useCases.changeStartRecipeTimeUseCase(group, it)
            }
        }
    }
}