package week.on.a.plate.screens.recipeTimeline.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.navigation.MenuDestination
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.dialogs.dialogTimePick.logic.TimePickViewModel
import week.on.a.plate.dialogs.editSelection.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.editSelection.state.EditSelectionUIState
import week.on.a.plate.dialogs.selectNStep.logic.SelectNStepViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.calendarMy.event.CalendarMyEvent
import week.on.a.plate.screens.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.recipeTimeline.event.RecipeTimelineEvent
import week.on.a.plate.screens.recipeTimeline.state.RecipeTimelineUIState
import week.on.a.plate.screens.recipeTimeline.state.StepTimelineMode
import week.on.a.plate.screens.specifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screens.specifySelection.logic.SpecifySelectionResult
import week.on.a.plate.screens.wrapperDatePicker.event.WrapperDatePickerEvent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RecipeTimelineViewModel @Inject constructor() : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    lateinit var state: RecipeTimelineUIState
    private lateinit var resultFlow: MutableStateFlow<RecipeTimelineUIState?>

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> {
                mainViewModel.onEvent(event)
            }

            is RecipeTimelineEvent -> {
                onEvent(event)
            }
        }
    }

    fun onEvent(event: RecipeTimelineEvent) {
        when (event) {
            RecipeTimelineEvent.AfterLast -> {
                afterLast(state.activeStepInd.value)
            }
            RecipeTimelineEvent.AfterLasts -> {
                if (state.activeStepInd.value>0){
                    val sublist = state.allUISteps.value.subList(0, state.activeStepInd.value)
                    val start = sublist.maxOf { it.start.value+it.duration.value }
                    state.allUISteps.value[state.activeStepInd.value].start.value = start
                }
            }
            RecipeTimelineEvent.Auto -> {
                state.allUISteps.value.forEachIndexed { ind, value ->
                    if (ind == 0) {
                        state.allUISteps.value[ind].start.value = 0
                    } else {
                        afterLast(ind)
                    }
                }
            }
            RecipeTimelineEvent.Back -> close()
            RecipeTimelineEvent.Done -> done()
            is RecipeTimelineEvent.SelectStep -> {
                state.activeStepInd.value = event.ind
            }

            RecipeTimelineEvent.SetDuration -> {
                getTime("Укажите длительность шага"){time->
                    state.allUISteps.value[state.activeStepInd.value].duration.value = time
                }
            }
            RecipeTimelineEvent.SetStart -> {
                getTime("Выберите время начала шага"){time->
                    state.allUISteps.value[state.activeStepInd.value].start.value = time
                }
            }

            RecipeTimelineEvent.StartWithN -> startWithN()
            RecipeTimelineEvent.AfterN -> afterN()

            RecipeTimelineEvent.ToEnd -> {
                val end = state.allUISteps.value.toMutableList().apply {
                    remove(state.allUISteps.value[state.activeStepInd.value])
                }.maxOf { it.start.value+it.duration.value }
                state.allUISteps.value[state.activeStepInd.value].start.value = end
            }
        }
    }

    private fun startWithN() {
        getSelectedStep{ind->
            val start = state.allUISteps.value[ind].start.value
            state.allUISteps.value[state.activeStepInd.value].start.value = start
        }
    }

    private fun afterN() {
        getSelectedStep{ind->
            val start = state.allUISteps.value[ind].start.value+state.allUISteps.value[ind].duration.value
            state.allUISteps.value[state.activeStepInd.value].start.value = start
        }
    }

    private fun getSelectedStep(use:(Int)->Unit){
        viewModelScope.launch {
            val vm = SelectNStepViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(state.allUISteps.value.size, use)
        }
    }

    private fun afterLast(ind:Int){
        if (ind>0){
            val start = state.allUISteps.value[ind-1].start.value + state.allUISteps.value[ind-1].duration.value
            state.allUISteps.value[ind].start.value = start
        }
    }

    private fun getTime(title:String, use:(Long)->Unit){
        viewModelScope.launch {
            val vm = TimePickViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(title, use)
        }
    }

    fun done() {
        close()
        mainViewModel.recipeCreateViewModel.onEvent(RecipeCreateEvent.Done)
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.NavigateBack)
        resultFlow.value = state
    }

    fun start(): Flow<RecipeTimelineUIState?> {
        val flow = MutableStateFlow<RecipeTimelineUIState?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet( stated:RecipeTimelineUIState, isFirstTimeline:Boolean, use: suspend (RecipeTimelineUIState) -> Unit) {
        state = stated

        if (isFirstTimeline){
            onEvent(RecipeTimelineEvent.AfterN)
        }

        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }
}