package week.on.a.plate.menuScreen.logic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.core.dialogs.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.core.dialogs.editRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.core.dialogs.editRecipePosition.logic.EditRecipePositionViewModel
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import week.on.a.plate.menuScreen.data.eventData.MenuEvent
import week.on.a.plate.menuScreen.data.stateData.MenuIUState
import week.on.a.plate.menuScreen.data.stateData.WeekState
import week.on.a.plate.menuScreen.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.menuScreen.logic.useCase.NavigationMenu
import week.on.a.plate.menuScreen.logic.useCase.SelectedRecipeManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val selectedRecipeManager: SelectedRecipeManager,
    private val navigationMenu: NavigationMenu,
) : ViewModel() {

    private var activeDay = LocalDate.of(2024, 8, 28)
        //LocalDate.now()
    lateinit var mainViewModel: MainViewModel


    val weekState: MutableStateFlow<WeekState> = MutableStateFlow(WeekState.Loading)
    val menuUIState = MenuIUState.MenuIUStateExample

    init {
        viewModelScope.launch {
            //sCRUDRecipeInMenu.menuR.insertNewWeek(WeekDataExample)
            updateWeek()
        }
    }

    fun updateWeek() {
        viewModelScope.launch {
            val week = sCRUDRecipeInMenu.menuR.fetWeekFun.getCurrentWeek(activeDay)
            if (week != null) {
                if (week.days.size < 7) {
                    val newWeek = sCRUDRecipeInMenu.getWeekParted(week)
                    weekState.value = WeekState.Success(newWeek)
                    menuUIState.titleTopBar.value = getTitle(newWeek)
                } else {
                    weekState.value = WeekState.Success(week)
                    menuUIState.titleTopBar.value = getTitle(week)
                }
            } else {
                val emptyweek = sCRUDRecipeInMenu.getEmptyWeek(activeDay)
                weekState.value = WeekState.Success(sCRUDRecipeInMenu.getEmptyWeek(activeDay))
                menuUIState.titleTopBar.value = getTitle(emptyweek)
            }
        }
    }

    private fun getTitle(weekView: WeekView): String {
        val formatterMonth = DateTimeFormatter.ofPattern("MMMM")
        val formatterDay = DateTimeFormatter.ofPattern("d")

        val start = weekView.days[0].date
        val end = weekView.days[6].date

        val month = start.format(formatterMonth).capitalize(Locale("ru"))
        val startDay = start.format(formatterDay)
        val endDay = end.format(formatterDay)

        return "$month $startDay-$endDay"
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> onEvent(event)
            is MenuEvent -> onEvent(event)
        }
    }

    fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.SwitchWeekOrDayView -> switchWeekOrDayView()
            is MenuEvent.SwitchEditMode -> switchEditMode()
            is MenuEvent.NavigateFromMenu -> {
                selectedRecipeManager.clear(menuUIState)
                navigationMenu.onEvent(event.navData)
            }

            is MenuEvent.ActionDBMenu -> {
                viewModelScope.launch {
                    sCRUDRecipeInMenu.onEvent(
                        event.actionWeekMenuDB,
                        selectedRecipeManager.getSelected(menuUIState)
                    )
                    updateWeek()
                }
            }

            is MenuEvent.ActionSelect -> selectedRecipeManager.onEvent(
                event.selectedData,
                menuUIState
            )

            is MenuEvent.GetSelIdAndCreate -> {
                viewModelScope.launch {
                    val selId = sCRUDRecipeInMenu.menuR.getSelIdOrCreate(
                        event.dateToLocalDate,
                        event.categoriesSelection
                    )
                    event.action(selId)
                }
            }

            is MenuEvent.ChangeWeek -> {
                activeDay = event.date
                updateWeek()
            }

            is MenuEvent.ChangePortionsCount -> TODO()
            MenuEvent.ChooseWeek -> chooseWeek()
            is MenuEvent.CreateDraft -> TODO()
            is MenuEvent.CreateIngredientPosition -> TODO()
            is MenuEvent.CreateNote -> TODO()
            is MenuEvent.CreatePosition -> TODO()
            is MenuEvent.EditDraft -> TODO()
            is MenuEvent.EditIngredientPosition -> TODO()
            is MenuEvent.EditNote -> TODO()
            is MenuEvent.EditPosition -> {
                when(event.position){
                    is Position.PositionRecipeView -> {
                        editPositionRecipe(event.position)
                    }
                    else->{

                    }
                }
            }
            is MenuEvent.RecipeToShopList -> TODO()
            MenuEvent.SelectedToShopList -> TODO()
        }
    }

    private fun editPositionRecipe(position: Position.PositionRecipeView) {
        viewModelScope.launch {
            val vm =  EditRecipePositionViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet() { event->
                when(event){
                    EditRecipePositionEvent.AddToCart -> TODO()
                    EditRecipePositionEvent.ChangePotionsCount -> TODO()
                    EditRecipePositionEvent.Delete -> TODO()
                    EditRecipePositionEvent.Double -> TODO()
                    EditRecipePositionEvent.FindReplace -> TODO()
                    EditRecipePositionEvent.Move -> {
                        Log.e("","")
                    }
                    EditRecipePositionEvent.Close -> {}
                }
            }
        }
    }

    private fun chooseWeek(){
        viewModelScope.launch {
            val vm =  ChooseWeekViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet() { date->
                onEvent(MenuEvent.ChangeWeek(date))
            }
        }
    }

    fun onEvent(event: MainEvent) {
        mainViewModel.onEvent(event)
    }

    private fun switchWeekOrDayView() {
        menuUIState.itsDayMenu.value = !menuUIState.itsDayMenu.value
        selectedRecipeManager.clear(menuUIState)
    }

    private fun switchEditMode() {
        menuUIState.editing.value = !menuUIState.editing.value
    }
}