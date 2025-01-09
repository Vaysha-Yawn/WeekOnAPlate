package week.on.a.plate.screens.cookPlanner.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerStepRepository
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.dialogs.cookStepMore.event.CookStepMoreEvent
import week.on.a.plate.dialogs.cookStepMore.logic.CookStepMoreDialogViewModel
import week.on.a.plate.dialogs.dialogTimePick.logic.TimePickViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.cookPlanner.event.CookPlannerEvent

class CookPlannerCardActionsUseCase (
    private val repository: CookPlannerStepRepository,
    private val update:() -> Unit,
    private val mainViewModel:MainViewModel
    ) {

    fun checkStep(event: CookPlannerEvent.CheckStep) {
        mainViewModel.viewModelScope.launch {
            repository.check(event.step)
            update()
        }
    }

    fun showStepMore(event: CookPlannerEvent.ShowStepMore) {
        CookStepMoreDialogViewModel.launch(mainViewModel){
            when (it) {
                CookStepMoreEvent.ChangeEndRecipeTime -> changeEndRecipeTime(event.groupView)
                CookStepMoreEvent.ChangeStartRecipeTime -> changeStartRecipeTime(event.groupView)
                CookStepMoreEvent.Close -> {}
                CookStepMoreEvent.ChangePortionsCount -> changePortionsCount(event.groupView)
                CookStepMoreEvent.Delete -> delete(event.groupView)
            }
        }
    }

    private fun getTime(title: Int, use: (Long) -> Unit) {
        TimePickViewModel.launch(mainViewModel, title, use)
    }

    private fun changePortionsCount(group: CookPlannerGroupView) {
        ChangePortionsCountViewModel.launch(mainViewModel, group.portionsCount){ portionsCount->
            mainViewModel.viewModelScope.launch {
                repository.changePortionsCount(group, portionsCount)
                update()
            }
        }
    }

    private fun delete(group: CookPlannerGroupView) {
        mainViewModel.viewModelScope.launch {
            repository.deleteGroup(group.id)
            update()
        }
    }

    private fun changeStartRecipeTime(group: CookPlannerGroupView) {
        getTime(R.string.when_recipe_start) {
            mainViewModel.viewModelScope.launch {
                repository.changeStartRecipeTime(group.id, it)
                update()
            }
        }
    }

    private fun changeEndRecipeTime(group: CookPlannerGroupView) {
        getTime(R.string.when_recipe_end) {
            mainViewModel.viewModelScope.launch {
                repository.changeEndRecipeTime(group.id, it)
                update()
            }
        }
    }
}