package week.on.a.plate.core.dialogs.SpecifySelection.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.core.dialogs.menu.datePicker.logic.DatePickerViewModel
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import week.on.a.plate.core.dialogs.SpecifySelection.data.SpecifySelectionEvent
import week.on.a.plate.core.dialogs.SpecifySelection.data.SpecifySelectionUIState
import week.on.a.plate.menuScreen.logic.useCase.CRUDRecipeInMenu
import javax.inject.Inject

@HiltViewModel
class SpecifySelectionViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
) : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    val state: SpecifySelectionUIState = SpecifySelectionUIState()
    private lateinit var resultFlow: MutableStateFlow<Long?>

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

    fun onEvent(event: SpecifySelectionEvent) {
        when (event) {
            SpecifySelectionEvent.Back -> close()
            SpecifySelectionEvent.ChooseDate -> chooseDate()
            SpecifySelectionEvent.Done -> done()
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

    fun getCategory(): CategoriesSelection? {
        if (!state.checkWeek.value && state.checkDayCategory.value == null) return null
        if (state.checkWeek.value) return CategoriesSelection.ForWeek
        return state.checkDayCategory.value
    }

    fun done() {
        close()
        val category =  getCategory() ?: return
        state.date.value?:return
        mainViewModel.viewModelScope.launch {
            val selId = sCRUDRecipeInMenu.menuR.getSelIdOrCreate(state.date.value!!,category)
            resultFlow.value = selId
        }
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.NavigateBack)
    }

    fun start(): Flow<Long?> {
        val flow = MutableStateFlow<Long?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(use: (Long) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}