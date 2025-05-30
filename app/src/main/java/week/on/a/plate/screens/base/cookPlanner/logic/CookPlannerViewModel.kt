package week.on.a.plate.screens.base.cookPlanner.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.getTitleWeek
import week.on.a.plate.screens.additional.recipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screens.additional.recipeDetails.navigation.RecipeDetailsNavParams
import week.on.a.plate.screens.base.cookPlanner.event.CookPlannerEvent
import week.on.a.plate.screens.base.cookPlanner.logic.stepMore.CookPlannerCardActions
import week.on.a.plate.screens.base.cookPlanner.logic.stepMore.usecases.CheckStepUseCase
import week.on.a.plate.screens.base.cookPlanner.state.CookPlannerUIState
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.base.wrapperDatePicker.logic.WrapperDatePickerManager
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class CookPlannerViewModel @Inject constructor(
    private val wrapperDatePickerManager: WrapperDatePickerManager,
    private val checkStepUseCase: CheckStepUseCase,
    private val getWeekUseCase: GetWeekUseCase,
    private val cookPlannerCardActions: CookPlannerCardActions
) : ViewModel() {

    val state: MutableState<CookPlannerUIState> = mutableStateOf(CookPlannerUIState())
    val dialogOpenParams: MutableState<DialogOpenParams?> = mutableStateOf(null)
    private var cookPlannerWrapperDatePickerManager: CookPlannerWrapperDatePickerManager =
        CookPlannerWrapperDatePickerManager(
            wrapperDatePickerManager = wrapperDatePickerManager,
            state.value.wrapperDatePickerUIState
        )
    val mainEvent: MutableState<MainEvent?> = mutableStateOf(null)

    init {
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
            Locale.getDefault()
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
            is MainEvent -> {
                mainEvent.value = event
            }

            is WrapperDatePickerEvent -> {
                wrapperDatePickerManager.onEvent(event, state.value.wrapperDatePickerUIState)
                viewModelScope.launch {
                    cookPlannerWrapperDatePickerManager.onEvent(
                        dialogOpenParams,
                        event,
                        viewModelScope,
                        ::update
                    )
                }
            }

            is CookPlannerEvent -> onEvent(event)
        }
    }

    fun onEvent(event: CookPlannerEvent) {
        viewModelScope.launch {
            when (event) {
                is CookPlannerEvent.CheckStep -> checkStepUseCase(event)
                is CookPlannerEvent.ShowStepMore -> cookPlannerCardActions.showStepMore(
                    event,
                    dialogOpenParams, viewModelScope,
                )

                is CookPlannerEvent.NavToFullStep ->
                    mainEvent.value = MainEvent.Navigate(
                        RecipeDetailsDestination, RecipeDetailsNavParams(
                            event.groupView.recipeId,
                            event.groupView.portionsCount
                        )
                    )
            }
        }
    }

}