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
import week.on.a.plate.dialogs.editSelection.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.editSelection.state.EditSelectionUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.calendarMy.event.CalendarMyEvent
import week.on.a.plate.screens.recipeTimeline.event.RecipeTimelineEvent
import week.on.a.plate.screens.recipeTimeline.state.RecipeTimelineUIState
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
    private lateinit var resultFlow: MutableStateFlow<RecipeTimelineUIState?>//???

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
            RecipeTimelineEvent.AfterLast -> TODO()
            RecipeTimelineEvent.AfterLasts -> TODO()
            RecipeTimelineEvent.AfterN -> TODO()
            RecipeTimelineEvent.Auto -> TODO()
            RecipeTimelineEvent.Back -> close()
            RecipeTimelineEvent.Done -> done()
            is RecipeTimelineEvent.SelectStep -> TODO()
            RecipeTimelineEvent.SetDuration -> TODO()
            RecipeTimelineEvent.SetStart -> TODO()
            RecipeTimelineEvent.StartWithN -> TODO()
            RecipeTimelineEvent.ToEnd -> TODO()
        }
    }

    fun done() {
        close()
      //todo
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.NavigateBack)
    }

    fun start(): Flow<RecipeTimelineUIState?> {
        val flow = MutableStateFlow<RecipeTimelineUIState?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(use: suspend (RecipeTimelineUIState) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }
}