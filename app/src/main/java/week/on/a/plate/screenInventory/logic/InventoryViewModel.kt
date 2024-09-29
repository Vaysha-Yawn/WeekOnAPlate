package week.on.a.plate.screenInventory.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.screenMenu.dialogs.datePicker.logic.DatePickerViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenSpecifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screenSpecifySelection.state.SpecifySelectionUIState
import week.on.a.plate.data.repository.tables.menu.week.WeekRepository
import week.on.a.plate.screenInventory.event.InventoryEvent
import week.on.a.plate.screenInventory.state.InventoryUIState
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
) : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    val state: InventoryUIState = InventoryUIState()
    private lateinit var resultFlow: MutableStateFlow<Pair<Long,Int>?>

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

    fun onEvent(event: InventoryEvent) {
        when (event) {
            InventoryEvent.Back -> close()
            InventoryEvent.ChooseDate -> chooseDate()
            InventoryEvent.Done -> done()
        }
    }

    private fun chooseDate() {
        viewModelScope.launch {
            val vm = DatePickerViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet() { date ->
                state.date.value = date
            }
        }
    }

    fun initValues(category: String) {
        when (category) {
            CategoriesSelection.ForWeek.fullName -> {
                state.checkWeek.value = true
                state.checkDayCategory.value = null
            }

            CategoriesSelection.NonPosed.fullName -> {
                state.checkWeek.value = false
                state.checkDayCategory.value = CategoriesSelection.NonPosed
            }

            CategoriesSelection.Breakfast.fullName -> {
                state.checkWeek.value = false
                state.checkDayCategory.value = CategoriesSelection.Breakfast
            }

            CategoriesSelection.Lunch.fullName -> {
                state.checkWeek.value = false
                state.checkDayCategory.value = CategoriesSelection.Lunch
            }

            CategoriesSelection.Dinner.fullName -> {
                state.checkWeek.value = false
                state.checkDayCategory.value = CategoriesSelection.Dinner
            }
        }
    }

    private fun getCategory(): CategoriesSelection? {
        if (!state.checkWeek.value && state.checkDayCategory.value == null) return null
        if (state.checkWeek.value) return CategoriesSelection.ForWeek
        return state.checkDayCategory.value
    }

    fun done() {
        close()
       //
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.NavigateBack)
    }

    fun start(): Flow<Pair<Long,Int>?> {
        val flow = MutableStateFlow<Pair<Long,Int>?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(use: (Long,Int) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value.first, value.second)
            }
        }
    }

}