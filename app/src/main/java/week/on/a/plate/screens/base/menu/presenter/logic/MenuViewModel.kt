package week.on.a.plate.screens.base.menu.presenter.logic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.getTitleWeek
import week.on.a.plate.screens.additional.recipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screens.additional.recipeDetails.navigation.RecipeDetailsNavParams
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

    val menuUIState = mutableStateOf(MenuUIState.MenuUIStateExample)
    private val activeDay = menuUIState.value.wrapperDatePickerUIState.activeDay

    val dialogOpenParams = mutableStateOf<DialogOpenParams?>(null)
    val mainEvent = mutableStateOf<MainEvent?>(null)

    init {
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
            is MainEvent -> mainEvent.value = event
            is WrapperDatePickerEvent -> onEvent(MenuEvent.ActionWrapperDatePicker(event))
        }
    }

    fun onEvent(event: MenuEvent) {

        when (event) {
            is MenuEvent.NavigateFromMenu -> {
                onEvent(MenuEvent.ClearSelected)
                when (event.navData) {
                    is MenuNavEvent.NavToFullRecipe -> {
                        onEvent(
                            MainEvent.Navigate(
                                RecipeDetailsDestination,
                                RecipeDetailsNavParams(
                                    event.navData.recId,
                                    event.navData.portionsCount
                                )
                            )
                        )
                    }
                }
            }

            is MenuEvent.ActionSelect -> selectedRecipeManager.onEvent(
                event.selectedEvent,
                menuUIState.value
            )

            is MenuEvent.GetSelIdAndCreate -> {
                viewModelScope.launch {
                    getSelAndCreate(
                        event.context,
                        dialogOpenParams,
                        ::onEvent
                    )
                }
            }

            is MenuEvent.CreatePosition -> viewModelScope.launch(Dispatchers.IO) {
                addPosition(
                    event.selId,
                    event.context,
                    dialogOpenParams,
                    ::onEvent
                )
            }

            is MenuEvent.EditPositionMore -> {
                viewModelScope.launch {
                    if (event.position is Position.PositionRecipeView) {
                        recipePositionActionsMore(
                            event.position,
                            dialogOpenParams,
                            ::onEvent
                        )
                    } else otherPositionActionsMore(
                        event.position,
                        dialogOpenParams,
                        ::onEvent
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
                    selectedRecipeManager::getSelected
                )
            }

            is MenuEvent.ActionWrapperDatePicker -> menuWrapperDatePickerManager.invoke(
                event.event,
                dialogOpenParams,
                ::updateWeek,
                menuUIState.value.wrapperDatePickerUIState,
                ::onEvent
            )

            is MenuEvent.SearchByDraft -> viewModelScope.launch {
                searchByDraft(
                    event.draft, ::onEvent
                )
            }

            is MenuEvent.CreateFirstNonPosedPosition -> viewModelScope.launch {
                createFirstNonPosedPosition(
                    event.context,
                    activeDay,
                    dialogOpenParams, ::onEvent
                )
            }

            is MenuEvent.EditOrDeleteSelection ->
                viewModelScope.launch(Dispatchers.IO) {
                    editOrDeleteSelection(event.sel, dialogOpenParams)
                }

            is MenuEvent.CreateSelection ->
                viewModelScope.launch(Dispatchers.IO) {
                    createSelection(event.date, event.isForWeek, dialogOpenParams)
                }

            is MenuEvent.CreateWeekSelIdAndCreatePosition ->
                viewModelScope.launch(Dispatchers.IO) {
                    createWeekSelIdAndCreatePos(
                        event.context,
                        activeDay,
                        dialogOpenParams,
                        ::onEvent
                    )
                }

            MenuEvent.ClearSelected -> {
                menuUIState.value =
                    menuUIState.value.copy(isAllSelected = false, chosenRecipes = mutableMapOf())
            }
        }
    }
}