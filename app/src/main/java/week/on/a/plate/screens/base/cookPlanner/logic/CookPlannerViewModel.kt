package week.on.a.plate.screens.base.cookPlanner.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.getTitleWeek
import week.on.a.plate.screens.base.cookPlanner.event.CookPlannerEvent
import week.on.a.plate.screens.base.cookPlanner.logic.stepMore.CookPlannerCardActions
import week.on.a.plate.screens.base.cookPlanner.logic.stepMore.usecases.CheckStepUseCase
import week.on.a.plate.screens.base.cookPlanner.logic.stepMore.usecases.UseCaseWrapperCookPlannerCardActions
import week.on.a.plate.screens.base.cookPlanner.state.CookPlannerUIState
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.base.wrapperDatePicker.logic.WrapperDatePickerManager
import javax.inject.Inject

@HiltViewModel
class CookPlannerViewModel @Inject constructor(
    private val wrapperDatePickerManager: WrapperDatePickerManager,
    private val checkStepUseCase: CheckStepUseCase,
    private val cardActionsUseCases: UseCaseWrapperCookPlannerCardActions,
    private val getWeekUseCase: GetWeekUseCase,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state: MutableState<CookPlannerUIState> = mutableStateOf(CookPlannerUIState())
    private lateinit var cookPlannerCardActions: CookPlannerCardActions
    private lateinit var cookPlannerWrapperDatePickerManager: CookPlannerWrapperDatePickerManager

    fun initWithMainVM(mainViewModel: MainViewModel) {
        cookPlannerCardActions = CookPlannerCardActions(
            mainViewModel, viewModelScope, cardActionsUseCases
        )
        cookPlannerWrapperDatePickerManager = CookPlannerWrapperDatePickerManager(
            wrapperDatePickerManager = wrapperDatePickerManager,
            mainViewModel,
            state.value.wrapperDatePickerUIState
        )
        val activeDate = state.value.wrapperDatePickerUIState.activeDay.value
        val startWeek = activeDate.minusDays(activeDate.dayOfWeek.ordinal.toLong())
        val endWeek = activeDate.plusDays(6 - activeDate.dayOfWeek.ordinal.toLong())
        state.value.wrapperDatePickerUIState.titleTopBar.value = getTitleWeek(startWeek, endWeek)
        viewModelScope.launch {
            update()
        }
    }

    suspend fun update() {
        val week = getWeekUseCase(
            state.value.wrapperDatePickerUIState.activeDay.value,
            mainViewModel.locale
        )

        val weekResult = week.keys.map {
            val isPlanned = false
            Pair(it, isPlanned)
        }

        state.value = CookPlannerUIState(mapOf(), weekResult, state.value.wrapperDatePickerUIState)

        week.entries.forEach { (date, flowList) ->
            viewModelScope.launch {
                flowList.collect { dayCook ->
                    val weekData = state.value.week.toMutableMap().apply {
                        this[date] = dayCook
                    }.toSortedMap(comparator = compareBy { it })

                    val weekResultUpdated = weekData.map { (it, list) ->
                        val isPlanned = list.isNotEmpty()
                        Pair(it, isPlanned)
                    }

                    state.value = state.value.copy(
                        week = weekData,
                        weekResult = weekResultUpdated
                    )
                }
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> mainViewModel.onEvent(event)

            is WrapperDatePickerEvent -> {
                wrapperDatePickerManager.onEvent(event, state.value.wrapperDatePickerUIState)
                cookPlannerWrapperDatePickerManager.onEvent(event, ::update)
            }

            is CookPlannerEvent -> onEvent(event)
        }
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