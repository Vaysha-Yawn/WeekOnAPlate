package week.on.a.plate.menuScreen.logic

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.data.example.WeekDataExample
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.menuScreen.data.eventData.MenuEvent
import week.on.a.plate.menuScreen.data.stateData.MenuIUState
import week.on.a.plate.menuScreen.data.stateData.WeekState
import week.on.a.plate.menuScreen.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.menuScreen.logic.useCase.DialogManager
import week.on.a.plate.menuScreen.logic.useCase.Navigation
import week.on.a.plate.menuScreen.logic.useCase.SelectedRecipeManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val selectedRecipeManager: SelectedRecipeManager,
    private val navigation: Navigation,
    private val dialogManager: DialogManager,
) : ViewModel() {

    lateinit var snackbarHostState: SnackbarHostState
    private var activeDay = LocalDate.now()
    //private val today = LocalDate.of(2024, 8, 28)
    val weekState: MutableStateFlow<WeekState> = MutableStateFlow(WeekState.Loading)
    val menuUIState = MenuIUState.MenuIUStateExample.apply {
        dialogState = dialogManager.activeDialog
    }

    init {
        viewModelScope.launch {
            //sCRUDRecipeInMenu.menuR.insertNewWeek(WeekDataExample)
            updateWeek()
        }
    }

    fun setNavController(navController: NavHostController){
        navigation.navController = navController
    }

    fun updateWeek(){
        viewModelScope.launch {
            val week = sCRUDRecipeInMenu.menuR.fetWeekFun.getCurrentWeek(activeDay)
            if (week!=null){
                if (week.days.size<7){
                    val newWeek = sCRUDRecipeInMenu.getWeekParted(week)
                    weekState.value = WeekState.Success(newWeek)
                    menuUIState.titleTopBar.value = getTitle(newWeek)
                }else{
                    weekState.value = WeekState.Success(week)
                    menuUIState.titleTopBar.value = getTitle(week)
                }
            }else{
                val emptyweek = sCRUDRecipeInMenu.getEmptyWeek(activeDay)
                weekState.value =  WeekState.Success(sCRUDRecipeInMenu.getEmptyWeek(activeDay))
                menuUIState.titleTopBar.value = getTitle(emptyweek)
            }
        }
    }

    private fun getTitle(weekView: WeekView):String{
        val formatterMonth = DateTimeFormatter.ofPattern("MMMM")
        val formatterDay = DateTimeFormatter.ofPattern("d")

        val start = weekView.days[0].date
        val end = weekView.days[6].date

        val month = start.format(formatterMonth).capitalize(Locale("ru"))
        val startDay = start.format(formatterDay)
        val endDay = end.format(formatterDay)

        return "$month $startDay-$endDay"
    }

    fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.SwitchWeekOrDayView -> switchWeekOrDayView()
            is MenuEvent.SwitchEditMode -> switchEditMode()
            is MenuEvent.NavigateFromMenu -> {
                selectedRecipeManager.clear(menuUIState)
                navigation.onEvent(event.navData)
            }
            is MenuEvent.OpenDialog -> dialogManager.openDialog(event.dialog, viewModelScope)
            is MenuEvent.CloseDialog -> dialogManager.closeDialog()
            is MenuEvent.ActionDBMenu -> {
                viewModelScope.launch {
                    sCRUDRecipeInMenu.onEvent(event.actionMenuDBData, selectedRecipeManager.getSelected(menuUIState))
                    updateWeek()
                }
            }

            is MenuEvent.ActionSelect -> selectedRecipeManager.onEvent(event.selectedData, menuUIState)
            is MenuEvent.GetSelIdAndCreate -> {
                viewModelScope.launch {
                    val selId = sCRUDRecipeInMenu.menuR.getSelIdOrCreate(event.dateToLocalDate, event.categoriesSelection)
                    event.action(selId)
                }
            }

            is MenuEvent.ChangeWeek -> {
                activeDay = event.date
                updateWeek()
            }

            is MenuEvent.ShowSnackBar -> {
                viewModelScope.launch {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    private fun switchWeekOrDayView(){
        menuUIState.itsDayMenu.value = !menuUIState.itsDayMenu.value
        selectedRecipeManager.clear(menuUIState)
    }

    private fun switchEditMode(){
        menuUIState.editing.value = !menuUIState.editing.value
    }
}