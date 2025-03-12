package week.on.a.plate.screens.base.menu.presenter.logic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.getTitleWeek
import week.on.a.plate.screens.base.menu.domain.dbusecase.GetWeekFlowUseCase
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import week.on.a.plate.screens.base.menu.presenter.event.MenuNavEvent
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.CreateSelectionOpenDialog
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.CreateWeekSelIdAndCreatePosOpenDialog
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.EditOrDeleteSelectionOpenDialog
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.GetSelAndCreateUseCase
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.OtherPositionActionsMoreOpenDialog
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.RecipePositionMoreOpenDialog
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.SearchByDraftUseCase
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition.AddPositionOpenDialog
import week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.shopList.SelectedToShopListNavToInventory
import week.on.a.plate.screens.base.menu.presenter.state.MenuUIState
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(
    //selected
    private val selectedToShopList: SelectedToShopListNavToInventory,
    private val selectedRecipeManager: SelectedRecipeManager,

    private val menuWrapperDatePickerManager: MenuWrapperDatePickerManager,

    //create pos
    private val getSelAndCreate: GetSelAndCreateUseCase,
    private val createFirstNonPosedPosition: CreateWeekSelIdAndCreatePosOpenDialog,
    private val addPosition: AddPositionOpenDialog,

    //act with pos
    private val recipePositionActionsMore: RecipePositionMoreOpenDialog,
    private val otherPositionActionsMore: OtherPositionActionsMoreOpenDialog,
    private val searchByDraft: SearchByDraftUseCase,

    private val getCurrentWeekFlow: GetWeekFlowUseCase,

    //act with selection
    private val createSelection: CreateSelectionOpenDialog,
    private val editOrDeleteSelection: EditOrDeleteSelectionOpenDialog,

    //sel and pos
    private val createWeekSelIdAndCreatePos: CreateWeekSelIdAndCreatePosOpenDialog,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val menuUIState = mutableStateOf(MenuUIState.MenuUIStateExample)
    private val activeDay = menuUIState.value.wrapperDatePickerUIState.activeDay

    fun initWithMainVM(mainVM: MainViewModel) {
        mainViewModel = mainVM
        updateWeek()
    }

    private fun updateWeek() {
        viewModelScope.launch {
            val flow = getCurrentWeekFlow(
                menuUIState.value.nonPosedFullName.value,
                menuUIState.value.forWeekFullName.value,
                activeDay.value,
                Locale.getDefault()
            )
            flow.collect { weekData ->
                if (weekData.days.isNotEmpty()) {
                    menuUIState.value.wrapperDatePickerUIState.titleTopBar.value =
                        getTitleWeek(weekData.days[0].date, weekData.days[6].date)
                }
                menuUIState.value = menuUIState.value.copy(week = weekData)
            }
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is MenuEvent -> onEvent(event)
            is MainEvent -> mainViewModel.onEvent(event)
            is WrapperDatePickerEvent -> onEvent(MenuEvent.ActionWrapperDatePicker(event))
        }
    }

    fun onEvent(event: MenuEvent) {

        when (event) {
            is MenuEvent.NavigateFromMenu -> {
                onEvent(MenuEvent.ClearSelected)
                when (event.navData) {
                    is MenuNavEvent.NavToFullRecipe -> {
                        mainViewModel.recipeDetailsViewModel.launch(
                            event.navData.recId,
                            event.navData.portionsCount
                        )
                    }
                }
            }

            is MenuEvent.ActionSelect -> selectedRecipeManager.onEvent(
                event.selectedEvent,
                menuUIState.value
            )

            is MenuEvent.GetSelIdAndCreate -> getSelAndCreate(
                event.context,
                mainViewModel,
                ::onEvent
            )

            is MenuEvent.CreatePosition -> viewModelScope.launch(Dispatchers.IO) {
                addPosition(
                    event.selId,
                    event.context,
                    mainViewModel
                )
            }

            is MenuEvent.EditPositionMore -> {
                viewModelScope.launch {
                    if (event.position is Position.PositionRecipeView) {
                        recipePositionActionsMore(
                            event.position,
                            mainViewModel,
                            ::onEvent
                        )
                    } else otherPositionActionsMore(
                        event.position,
                        mainViewModel,
                        mainViewModel::onEvent, ::onEvent
                    )
                }
            }

            MenuEvent.DeleteSelected ->
                viewModelScope.launch {
                    selectedRecipeManager.deleteSelected(menuUIState.value, ::onEvent)
                }

            MenuEvent.SelectedToShopList -> viewModelScope.launch {
                selectedToShopList(
                    menuUIState.value,
                    ::onEvent,
                    mainViewModel,
                    selectedRecipeManager::getSelected
                )
            }

            is MenuEvent.ActionWrapperDatePicker -> menuWrapperDatePickerManager.invoke(
                event.event,
                mainViewModel,
                ::updateWeek,
                menuUIState.value.wrapperDatePickerUIState,
                ::onEvent
            )

            is MenuEvent.SearchByDraft -> viewModelScope.launch {
                searchByDraft(
                    event.draft, mainViewModel
                )
            }

            is MenuEvent.CreateFirstNonPosedPosition -> viewModelScope.launch {
                createFirstNonPosedPosition(
                    event.context,
                    activeDay,
                    mainViewModel,
                )
            }

            is MenuEvent.EditOrDeleteSelection ->
                viewModelScope.launch(Dispatchers.IO) {
                    editOrDeleteSelection(event.sel, mainViewModel)
                }

            is MenuEvent.CreateSelection ->
                viewModelScope.launch(Dispatchers.IO) {
                    createSelection(event.date, event.isForWeek, mainViewModel)
                }

            is MenuEvent.CreateWeekSelIdAndCreatePosition ->
                viewModelScope.launch(Dispatchers.IO) {
                    createWeekSelIdAndCreatePos(event.context, activeDay, mainViewModel)
                }

            MenuEvent.ClearSelected -> {
                menuUIState.value =
                    menuUIState.value.copy(isAllSelected = false, chosenRecipes = mutableMapOf())
            }
        }
    }
}