package week.on.a.plate.screens.base.cookPlanner.logic.stepMore

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.dialogs.cookStepMore.event.CookStepMoreEvent
import week.on.a.plate.dialogs.cookStepMore.logic.CookStepMoreDialogViewModel
import week.on.a.plate.dialogs.timePick.logic.TimePickViewModel
import week.on.a.plate.screens.base.cookPlanner.event.CookPlannerEvent
import week.on.a.plate.screens.base.cookPlanner.logic.stepMore.usecases.UseCaseWrapperCookPlannerCardActions
import javax.inject.Inject


class CookPlannerCardActions @Inject constructor(
    private val useCases: UseCaseWrapperCookPlannerCardActions
) {

    suspend fun showStepMore(
        event: CookPlannerEvent.ShowStepMore,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
    ) = coroutineScope {
        val params = CookStepMoreDialogViewModel.CookStepMoreDialogDialogParams {
            scope.launch(Dispatchers.IO) {
                when (it) {
                    CookStepMoreEvent.ChangeEndRecipeTime -> {
                        changeEndRecipeTime(dialogOpenParams, event, scope)
                    }

                    CookStepMoreEvent.ChangeStartRecipeTime ->
                        changeStartRecipeTime(dialogOpenParams, event.groupView, scope)

                    CookStepMoreEvent.Close -> {}
                    CookStepMoreEvent.ChangePortionsCount -> changePortionsCount(
                        event.groupView,
                        dialogOpenParams, scope,
                    )

                    CookStepMoreEvent.Delete ->
                        useCases.deleteCookPlannerGroupUseCase(
                            event.groupView
                        )
                }
            }
        }
        dialogOpenParams.value = params
    }

    private suspend fun changeEndRecipeTime(
        dialogOpenParams: MutableState<DialogOpenParams?>,
        event: CookPlannerEvent.ShowStepMore,
        scope: CoroutineScope,
    ) = coroutineScope {
        getTime(dialogOpenParams, R.string.when_recipe_end) { time ->
            scope.launch(Dispatchers.IO) {
                useCases.changeEndRecipeTimeUseCase(event.groupView, time)
            }
        }
    }

    private fun getTime(
        dialogOpenParams: MutableState<DialogOpenParams?>,
        title: Int,
        use: (Long) -> Unit
    ) {
        val params = TimePickViewModel.TimePickDialogParams(title, use)
        dialogOpenParams.value = params
    }

    private suspend fun changePortionsCount(
        group: CookPlannerGroupView,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
    ) = coroutineScope {
        val params = ChangePortionsCountViewModel.ChangePortionsCountDialogParams(
            group.portionsCount
        ) { portionsCount ->
            scope.launch(Dispatchers.IO) {
                useCases.changePortionsCountUseCase(group, portionsCount)
            }
        }
        dialogOpenParams.value = params
    }

    private suspend fun changeStartRecipeTime(
        dialogOpenParams: MutableState<DialogOpenParams?>, group: CookPlannerGroupView,
        scope: CoroutineScope,
    ) = coroutineScope {
        getTime(dialogOpenParams, R.string.when_recipe_start) {
            scope.launch(Dispatchers.IO) {
                useCases.changeStartRecipeTimeUseCase(group, it)
            }
        }
    }
}