package week.on.a.plate.screens.cookPlanner.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.getTitleWeek
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.cookPlanner.event.CookPlannerEvent
import week.on.a.plate.screens.cookPlanner.state.CookPlannerUIState
import week.on.a.plate.core.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.core.wrapperDatePicker.logic.WrapperDatePickerManager
import week.on.a.plate.screens.cookPlanner.logic.stepMore.CookPlannerCardActions
import week.on.a.plate.screens.cookPlanner.logic.stepMore.usecases.CheckStepUseCase
import week.on.a.plate.screens.cookPlanner.logic.stepMore.usecases.UseCaseWrapperCookPlannerCardActions
import javax.inject.Inject

@HiltViewModel
class CookPlannerViewModel @Inject constructor(
    private val wrapperDatePickerManager: WrapperDatePickerManager,
    private val checkStepUseCase: CheckStepUseCase,
    private val cardActionsUseCases: UseCaseWrapperCookPlannerCardActions,
    private val getWeekUseCase: GetWeekUseCase,
    ) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state: CookPlannerUIState = CookPlannerUIState()
    private lateinit var cookPlannerCardActions: CookPlannerCardActions
    private lateinit var cookPlannerWrapperDatePickerManager: CookPlannerWrapperDatePickerManager

    fun initWithMainVM(mainViewModel: MainViewModel) {
        cookPlannerCardActions = CookPlannerCardActions(
            mainViewModel, viewModelScope, cardActionsUseCases
        )
        cookPlannerWrapperDatePickerManager = CookPlannerWrapperDatePickerManager(
            wrapperDatePickerManager = wrapperDatePickerManager,
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
        val week = getWeekUseCase(state.wrapperDatePickerUIState.activeDay.value)
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
                cookPlannerWrapperDatePickerManager.onEvent(event)
            }

            is CookPlannerEvent -> onEvent(event)
        }
        update()
    }

    fun onEvent(event: CookPlannerEvent) {
        when (event) {
            is CookPlannerEvent.CheckStep -> viewModelScope.launch { checkStepUseCase(event) }
            is CookPlannerEvent.ShowStepMore -> cookPlannerCardActions.showStepMore(event)
            is CookPlannerEvent.NavToFullStep -> mainViewModel.recipeDetailsViewModel.launch(
                event.groupView.recipeId,
                event.groupView.portionsCount
            )
        }
    }

}