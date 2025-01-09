package week.on.a.plate.screens.cookPlanner.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.getTitleWeek
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerStepRepository
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.cookPlanner.event.CookPlannerEvent
import week.on.a.plate.screens.cookPlanner.state.CookPlannerUIState
import week.on.a.plate.core.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.core.wrapperDatePicker.logic.WrapperDatePickerManager
import javax.inject.Inject

@HiltViewModel
class CookPlannerViewModel @Inject constructor(
    private val wrapperDatePickerManager: WrapperDatePickerManager,
    val repository: CookPlannerStepRepository
) : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    val state: CookPlannerUIState = CookPlannerUIState()
    private lateinit var cookPlannerCardActionsUseCase: CookPlannerCardActionsUseCase
    private lateinit var cookPlannerWrapperDatePickerUseCase: CookPlannerWrapperDatePickerUseCase

    fun initWithMainVM(mainViewModel: MainViewModel) {
        cookPlannerCardActionsUseCase = CookPlannerCardActionsUseCase(
            repository,
            ::update,
            mainViewModel
        )
        cookPlannerWrapperDatePickerUseCase = CookPlannerWrapperDatePickerUseCase(
            wrapperDatePickerManager = wrapperDatePickerManager,
            ::update,
            mainViewModel,
            state.wrapperDatePickerUIState
        )
    }

    init {
        val activeDate = state.wrapperDatePickerUIState.activeDay.value
        val startWeek = activeDate.minusDays(activeDate.dayOfWeek.ordinal.toLong())
        val endWeek = activeDate.plusDays(6 - activeDate.dayOfWeek.ordinal.toLong())
        state.wrapperDatePickerUIState.titleTopBar.value = getTitleWeek(startWeek, endWeek)
        update()
    }

    fun update() {
        state.week.value = mapOf()
        val week = repository.getWeek(state.wrapperDatePickerUIState.activeDay.value)
        week.entries.forEach { dateAndList ->
            viewModelScope.launch {
                dateAndList.value.stateIn(viewModelScope).collect {
                    state.week.value = state.week.value.toMutableMap().apply {
                        this[dateAndList.key] = it
                    }.toMap().toSortedMap(comparator = compareBy { it }).toMap()
                }
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> mainViewModel.onEvent(event)

            is WrapperDatePickerEvent -> {
                wrapperDatePickerManager.onEvent(event, state.wrapperDatePickerUIState)
                cookPlannerWrapperDatePickerUseCase.onEvent(event)
            }

            is CookPlannerEvent -> onEvent(event)
        }
    }

    fun onEvent(event: CookPlannerEvent) {
        when (event) {
            is CookPlannerEvent.CheckStep -> cookPlannerCardActionsUseCase.checkStep(event)
            is CookPlannerEvent.ShowStepMore -> cookPlannerCardActionsUseCase.showStepMore(event)
            is CookPlannerEvent.NavToFullStep -> mainViewModel.recipeDetailsViewModel.launch(
                event.groupView.recipeId,
                event.groupView.portionsCount
            )
        }
    }

}