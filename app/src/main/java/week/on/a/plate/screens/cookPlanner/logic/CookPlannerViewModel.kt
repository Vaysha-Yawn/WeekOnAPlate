package week.on.a.plate.screens.cookPlanner.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.data.dataView.week.getTitleWeek
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerStepRepository
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.dialogs.cookStepMore.event.CookStepMoreEvent
import week.on.a.plate.dialogs.cookStepMore.logic.CookStepMoreDialogViewModel
import week.on.a.plate.dialogs.dialogTimePick.logic.TimePickViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.cookPlanner.event.CookPlannerEvent
import week.on.a.plate.screens.cookPlanner.state.CookPlannerUIState
import week.on.a.plate.screens.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.wrapperDatePicker.logic.WrapperDatePickerManager
import javax.inject.Inject

@HiltViewModel
class CookPlannerViewModel @Inject constructor(
    private val wrapperDatePickerManager: WrapperDatePickerManager,
    private val repository: CookPlannerStepRepository
) : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    val state: CookPlannerUIState = CookPlannerUIState()

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
            is MainEvent -> { mainViewModel.onEvent(event) }

            is WrapperDatePickerEvent -> {
                wrapperDatePickerManager.onEvent(event, state.wrapperDatePickerUIState)
                when (event) {
                    is WrapperDatePickerEvent.ChangeWeek -> {
                        wrapperDatePickerManager.changeWeek(
                            event.date,
                            state.wrapperDatePickerUIState
                        ) { date ->
                            state.wrapperDatePickerUIState.activeDay.value = date
                            state.wrapperDatePickerUIState.activeDayInd.value =
                                date.dayOfWeek.ordinal
                            update()
                        }
                    }

                    WrapperDatePickerEvent.ChooseWeek -> {
                        wrapperDatePickerManager.chooseWeek(
                            mainViewModel,
                            state.wrapperDatePickerUIState,
                            false
                        ) { date ->
                            state.wrapperDatePickerUIState.activeDay.value = date
                            state.wrapperDatePickerUIState.activeDayInd.value =
                                date.dayOfWeek.ordinal
                            update()
                        }
                    }

                    WrapperDatePickerEvent.SwitchEditMode -> {}
                    WrapperDatePickerEvent.SwitchWeekOrDayView -> {}
                }
            }
            is CookPlannerEvent -> onEvent(event)
        }
    }

    fun onEvent(event: CookPlannerEvent) {
        when (event) {
            is CookPlannerEvent.CheckStep -> {
                viewModelScope.launch {
                    repository.check(event.step)
                    update()
                }
            }

            is CookPlannerEvent.ShowStepMore -> {
                val vm = CookStepMoreDialogViewModel()
                vm.mainViewModel = mainViewModel
                viewModelScope.launch {
                    mainViewModel.onEvent(MainEvent.OpenDialog(vm))
                    vm.launchAndGet {
                        when (it) {
                            CookStepMoreEvent.ChangeEndRecipeTime -> changeEndRecipeTime(event.groupView)
                            CookStepMoreEvent.ChangeStartRecipeTime -> changeStartRecipeTime(event.groupView)
                            CookStepMoreEvent.Close -> {}
                            CookStepMoreEvent.ChangePortionsCount -> changePortionsCount(event.groupView)
                            CookStepMoreEvent.Delete -> delete(event.groupView)
                        }
                    }
                }
            }

            is CookPlannerEvent.NavToFullStep -> {
                mainViewModel.recipeDetailsViewModel.launch(event.groupView.recipeId, event.groupView.portionsCount)
            }
        }
    }

    private fun getTime(title: String, use: (Long) -> Unit) {
        mainViewModel.viewModelScope.launch {
            val vm = TimePickViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(title) { timeSec ->
                use(timeSec)
            }
        }
    }

    private fun changePortionsCount(group: CookPlannerGroupView) {
        viewModelScope.launch {
            val vm = ChangePortionsCountViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(group.portionsCount) { portionsCount ->
                viewModelScope.launch {
                    repository.changePortionsCount(group, portionsCount)
                    update()
                }
            }
        }
    }

    private fun delete(group: CookPlannerGroupView) {
        viewModelScope.launch {
            repository.deleteGroup(group.id)
            update()
        }
    }

    private fun changeStartRecipeTime(group: CookPlannerGroupView) {
        getTime("Во сколько начать приготовление?") {
            mainViewModel.viewModelScope.launch {
                repository.changeStartRecipeTime(group.id, it)
                update()
            }
        }
    }

    private fun changeEndRecipeTime(group: CookPlannerGroupView) {
        getTime("Ко скольки рассчитать приготовление?") {
            mainViewModel.viewModelScope.launch {
                repository.changeEndRecipeTime(group.id, it)
                update()
            }
        }
    }

}