package week.on.a.plate.screens.cookPlanner.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.navigation.MenuDestination
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.dialogs.editSelection.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.editSelection.state.EditSelectionUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.cookPlanner.event.CookPlannerEvent
import week.on.a.plate.screens.cookPlanner.state.CookPlannerUIState
import week.on.a.plate.screens.specifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screens.specifySelection.logic.SpecifySelectionResult
import week.on.a.plate.screens.specifySelection.state.SpecifySelectionUIState
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CookPlannerViewModel @Inject constructor() : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    val state: CookPlannerUIState = CookPlannerUIState()

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> {
                mainViewModel.onEvent(event)
            }

            is SpecifySelectionEvent -> {
                onEvent(event)
            }
        }
    }

    fun onEvent(event: CookPlannerEvent) {
        when (event) {
            is CookPlannerEvent.AddTimeStep -> TODO()
            is CookPlannerEvent.ChangeTimeEndRecipe -> TODO()
            is CookPlannerEvent.ChangeTimeStartRecipe -> TODO()
            is CookPlannerEvent.CheckStep -> TODO()
            is CookPlannerEvent.MoveTimeStep -> TODO()
            is CookPlannerEvent.ShowStepMore -> TODO()
            is CookPlannerEvent.StartTimer -> TODO()
        }
    }

}