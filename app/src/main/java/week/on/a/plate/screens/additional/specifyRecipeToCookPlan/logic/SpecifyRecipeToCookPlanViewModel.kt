package week.on.a.plate.screens.additional.specifyRecipeToCookPlan.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.EmptyNavParams
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.navigation.CookPlannerDestination
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.room.cookPlanner.CookPlannerStepRepository
import week.on.a.plate.data.repository.room.recipe.recipe.RecipeRepository
import week.on.a.plate.dialogs.calendarMy.event.CalendarMyEvent
import week.on.a.plate.dialogs.calendarMy.logic.CalendarMyUseCase
import week.on.a.plate.dialogs.calendarMy.state.StateCalendarMy
import week.on.a.plate.dialogs.timePick.logic.TimePickViewModel
import week.on.a.plate.screens.additional.specifyRecipeToCookPlan.event.SpecifyRecipeToCookPlanEvent
import week.on.a.plate.screens.additional.specifyRecipeToCookPlan.state.SpecifyRecipeToCookPlanUIState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class SpecifyRecipeToCookPlanViewModel @Inject constructor(
    private val calendarMyUseCase: CalendarMyUseCase,
    private val cookPlannerStepRepository: CookPlannerStepRepository,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    val state: SpecifyRecipeToCookPlanUIState = SpecifyRecipeToCookPlanUIState()
    var stateCalendar: StateCalendarMy = StateCalendarMy.emptyState
    var recipe: RecipeView? = null
    var portionsCount: Int? = null

    val dialogOpenParams: MutableState<DialogOpenParams?> = mutableStateOf(null)
    val mainEvent = mutableStateOf<MainEvent?>(null)

    init {
        val firstRow = calendarMyUseCase.getFirstRow(Locale.getDefault())
        stateCalendar.firstRow.value = firstRow
        val now = LocalDate.now()
        viewModelScope.launch {
            val allMonthDay = calendarMyUseCase.getAllMonthDays(now.year, now.monthValue, false)
            stateCalendar.allMonthDayAndIsPlanned.value = allMonthDay
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> mainEvent.value = event

            is CalendarMyEvent -> calendarMyUseCase.onEvent(event, stateCalendar, false)

            is SpecifyRecipeToCookPlanEvent -> onEvent(event)
        }
    }

    fun onEvent(event: SpecifyRecipeToCookPlanEvent) {
        when (event) {
            SpecifyRecipeToCookPlanEvent.Close -> close()
            SpecifyRecipeToCookPlanEvent.Done -> done()
            is SpecifyRecipeToCookPlanEvent.OpenTimePick -> openTimePick()
            is SpecifyRecipeToCookPlanEvent.SelectDate -> {
                state.date.value = event.date
            }

            SpecifyRecipeToCookPlanEvent.SwitchStartEnd -> {
                state.isStart.value = !state.isStart.value
            }
        }
    }

    private fun openTimePick() {
        val params = TimePickViewModel.TimePickDialogParams(R.string.select_time_cook) { time ->
            state.time.value = LocalTime.ofSecondOfDay(time)
        }
        dialogOpenParams.value = params
    }

    fun done() {
        viewModelScope.launch {
            if (recipe == null) close()
            else
                if (state.isStart.value) cookPlannerStepRepository.insertGroupByStart(
                    recipe!!,
                    LocalDateTime.of(state.date.value, state.time.value),
                    portionsCount
                )
                else cookPlannerStepRepository.insertGroupByEnd(
                    recipe!!,
                    LocalDateTime.of(state.date.value, state.time.value),
                    portionsCount
                )

            mainEvent.value = MainEvent.NavigateBack
            mainEvent.value = MainEvent.Navigate(CookPlannerDestination, EmptyNavParams)
        }
    }

    fun close() {
        mainEvent.value = MainEvent.NavigateBack
    }

    fun launchAndGet(recipePos: Position.PositionRecipeView) {
        viewModelScope.launch {
            calendarMyUseCase.updateMonthValue(stateCalendar, false)
            recipe = recipeRepository.getRecipe(recipePos.recipe.id)
            portionsCount = recipePos.portionsCount
        }
    }
}