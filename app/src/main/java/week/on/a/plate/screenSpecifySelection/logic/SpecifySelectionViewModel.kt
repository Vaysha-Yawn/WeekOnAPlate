package week.on.a.plate.screenSpecifySelection.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.core.Event
import week.on.a.plate.core.navigation.MenuScreen
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenSpecifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screenSpecifySelection.state.SpecifySelectionUIState
import week.on.a.plate.data.repository.tables.menu.selection.WeekRepository
import week.on.a.plate.dialogEditOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogEditOneString.state.EditOneStringUIState
import week.on.a.plate.screenMenu.event.MenuEvent
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SpecifySelectionViewModel @Inject constructor(
    private val weekRepository: WeekRepository,

) : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    val state: SpecifySelectionUIState = SpecifySelectionUIState()
    private lateinit var resultFlow: MutableStateFlow<SpecifySelectionResult?>

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
            SpecifySelectionEvent.AddCustomSelection -> addCustomSelection()
            is SpecifySelectionEvent.UpdatePreview -> updatePreview(event.date)
            is SpecifySelectionEvent.ApplyDate -> applyDate(event.date)
        }
    }

    private fun applyDate(date: LocalDate?) {
        state.isDateChooseActive.value = false
        state.date.value = date
    }

    private fun updatePreview(date: LocalDate?) {
        state.date.value = date
        if (date!=null){
            viewModelScope.launch {
                val selections = weekRepository.getSelectionsByDate(state.date.value!!)
                state.dayViewPreview.value = selections
            }
        }

    }

    private fun addCustomSelection() {
        viewModelScope.launch {
            val vm = EditOneStringViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(EditOneStringUIState(startTitle = "Добавить приём пищи", startPlaceholder = "Завтрак...")){ note->
                state.allSelectionsIdDay.value = state.allSelectionsIdDay.value.toMutableList().apply {
                    add(note)
                }
                state.checkWeek.value = false
                state.checkDayCategory.value = note
            }
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        }
    }

    private fun chooseDate() {
        state.isDateChooseActive.value = true
    }

    fun updateSelections(){
        if (state.date.value==null) return
        viewModelScope.launch {
            val allSelections = weekRepository.getSelectionsByDate(state.date.value!!)
            val listSelName = if (allSelections.isEmpty()){
                listOf<String>(CategoriesSelection.NonPosed.fullName)
            }else{
                allSelections.map { it.name }
            }
            state.allSelectionsIdDay.value = listSelName
        }
    }

    private fun getCategory(): String? {
        if (!state.checkWeek.value && state.checkDayCategory.value == null) return null
        if (state.checkWeek.value) return CategoriesSelection.ForWeek.fullName
        return state.checkDayCategory.value
    }

    fun done() {
        close()
        val category =  getCategory() ?: return
        state.date.value?:return
        mainViewModel.viewModelScope.launch {
            val selId = weekRepository.getSelIdOrCreate(state.date.value!!, state.checkWeek.value, category, mainViewModel.locale)
            resultFlow.value = SpecifySelectionResult(selId, state.date.value!!,  state.portionsCount.intValue)
        }
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.NavigateBack)
    }

    fun start(): Flow<SpecifySelectionResult?> {
        val flow = MutableStateFlow<SpecifySelectionResult?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(use: suspend (SpecifySelectionResult) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
                mainViewModel.menuViewModel.onEvent(MenuEvent.ChangeWeek(value.date))
                mainViewModel.nav.navigate(MenuScreen)
            }
        }
    }
}