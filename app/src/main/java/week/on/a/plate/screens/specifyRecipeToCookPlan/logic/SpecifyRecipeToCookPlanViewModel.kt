package week.on.a.plate.screens.specifyRecipeToCookPlan.logic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.navigation.CookPlannerDestination
import week.on.a.plate.core.navigation.MenuDestination
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerStepRepository
import week.on.a.plate.data.repository.tables.menu.selection.WeekRepository
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.dialogs.dialogTimePick.logic.TimePickViewModel
import week.on.a.plate.dialogs.editSelection.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.editSelection.state.EditSelectionUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.calendarMy.event.CalendarMyEvent
import week.on.a.plate.screens.calendarMy.logic.CalendarMyUseCase
import week.on.a.plate.screens.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.specifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screens.specifySelection.state.SpecifySelectionUIState
import week.on.a.plate.screens.calendarMy.state.StateCalendarMy
import week.on.a.plate.screens.specifyRecipeToCookPlan.event.SpecifyRecipeToCookPlanEvent
import week.on.a.plate.screens.specifyRecipeToCookPlan.state.SpecifyRecipeToCookPlanUIState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SpecifyRecipeToCookPlanViewModel @Inject constructor(
    private val calendarMyUseCase : CalendarMyUseCase,
    private val cookPlannerStepRepository: CookPlannerStepRepository,
    private val recipeRepository: RecipeRepository,
    ) : ViewModel() {
     lateinit var mainViewModel: MainViewModel
    val state: SpecifyRecipeToCookPlanUIState = SpecifyRecipeToCookPlanUIState()
    var stateCalendar : StateCalendarMy = StateCalendarMy.emptyState
    var recipe:RecipeView? = null

    init {
        val firstRow =  calendarMyUseCase.getFirstRow(Locale.getDefault())
        stateCalendar.firstRow.value = firstRow
        val now = LocalDate.now()
        viewModelScope.launch {
            val allMonthDay = calendarMyUseCase.getAllMonthDays(now.year, now.monthValue, false)
            stateCalendar.allMonthDayAndIsPlanned.value = allMonthDay
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> {
                mainViewModel.onEvent(event)
            }
            is CalendarMyEvent -> {
                calendarMyUseCase.onEvent(event, stateCalendar, false)
            }
            is SpecifyRecipeToCookPlanEvent -> {
                onEvent(event)
            }
        }
    }

    fun onEvent(event: SpecifyRecipeToCookPlanEvent) {
        when (event) {
            SpecifyRecipeToCookPlanEvent.Close -> close()
            SpecifyRecipeToCookPlanEvent.Done -> done()
            SpecifyRecipeToCookPlanEvent.OpenTimePick -> openTimePick()
            is SpecifyRecipeToCookPlanEvent.SelectDate -> {state.date.value = event.date }
            SpecifyRecipeToCookPlanEvent.SwitchStartEnd -> {state.isStart.value = !state.isStart.value}
        }
    }

    private fun openTimePick(){
        mainViewModel.viewModelScope.launch {
            val vm = TimePickViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(){time->
                state.time.value = LocalTime.ofSecondOfDay((time).toLong())
            }
        }
    }

    fun done() {
        viewModelScope.launch {
            if (recipe == null) close()
            else
                if (state.isStart.value){
                    cookPlannerStepRepository.insertGroupByStart(recipe!!, LocalDateTime.of(state.date.value, state.time.value))
                }else{
                    cookPlannerStepRepository.insertGroupByEnd(recipe!!, LocalDateTime.of(state.date.value, state.time.value))
                }
                mainViewModel.nav.popBackStack()
                mainViewModel.nav.navigate(CookPlannerDestination)
        }
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.NavigateBack)
    }

    fun launchAndGet(recipePos:Position.PositionRecipeView) {
        viewModelScope.launch {
            calendarMyUseCase.updateMonthValue(stateCalendar, false)
            recipe = recipeRepository.getRecipe(recipePos.recipe.id)
        }
    }
}