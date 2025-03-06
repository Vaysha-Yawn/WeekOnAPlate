package week.on.a.plate.screens.base.menu.logic

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
import week.on.a.plate.screens.base.menu.event.MenuEvent
import week.on.a.plate.screens.base.menu.event.MenuNavEvent
import week.on.a.plate.screens.base.menu.logic.usecase.OtherPositionActionsMore
import week.on.a.plate.screens.base.menu.logic.usecase.SelectedRecipeManager
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.GetWeekFlowUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.AddPositionOpenDialog
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.CreateSelectionOpenDialog
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.CreateWeekSelIdAndCreatePosOpenDialog
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.EditOrDeleteSelectionOpenDialog
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.EditOtherPositionUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.GetSelAndCreateUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.draft.SearchByDraftUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.recipe.FindRecipeAndReplaceNavToScreen
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.recipe.RecipePositionDialogActionsMore
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.shopList.RecipeToShopListWithInventoryUseCase
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.shopList.SelectedToShopListUseCase
import week.on.a.plate.screens.base.menu.state.MenuUIState
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(
    private val selectedRecipeManager: SelectedRecipeManager,
    private val menuWrapperDatePickerManager: MenuWrapperDatePickerManager,
    private val getSelAndCreate: GetSelAndCreateUseCase,
    private val recipePositionDialogActionsMore: RecipePositionDialogActionsMore,
    private val otherPositionActionsMore: OtherPositionActionsMore,
    private val editOtherPosition: EditOtherPositionUseCase,
    private val addPosition: AddPositionOpenDialog,
    private val selectedToShopList: SelectedToShopListUseCase,
    private val recipeToShopList: RecipeToShopListWithInventoryUseCase,
    private val findRecipeAndReplace: FindRecipeAndReplaceNavToScreen,
    private val searchByDraft: SearchByDraftUseCase,
    private val createFirstNonPosedPosition: CreateWeekSelIdAndCreatePosOpenDialog,
    private val getCurrentWeekFlow: GetWeekFlowUseCase,

    private val createSelection: CreateSelectionOpenDialog,
    private val editOrDeleteSelection: EditOrDeleteSelectionOpenDialog,
    private val createWeekSelIdAndCreatePos: CreateWeekSelIdAndCreatePosOpenDialog,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val menuUIState = MenuUIState.MenuUIStateExample
    private val activeDay = menuUIState.wrapperDatePickerUIState.activeDay

    fun initWithMainVM(mainVM: MainViewModel) {
        mainViewModel = mainVM
        updateWeek()
    }

    private fun updateWeek() {
        viewModelScope.launch {
            val flow = getCurrentWeekFlow(
                menuUIState.nonPosedFullName.value,
                menuUIState.forWeekFullName.value,
                activeDay.value,
                Locale.getDefault()
            )
            flow.collect { week ->
                if (week.days.isNotEmpty()) {
                    menuUIState.wrapperDatePickerUIState.titleTopBar.value =
                        getTitleWeek(week.days[0].date, week.days[6].date)
                }
                menuUIState.week.value = week
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
                selectedRecipeManager.clear(menuUIState)
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
                menuUIState
            )

            is MenuEvent.GetSelIdAndCreate -> getSelAndCreate(
                event.context,
                mainViewModel
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
                        recipePositionDialogActionsMore(
                            event.position,
                            mainViewModel,
                            ::onEvent
                        )
                    } else otherPositionActionsMore(
                        event.position,
                        mainViewModel,
                        ::onEvent,
                        mainViewModel::onEvent
                    )
                }
            }

            is MenuEvent.EditOtherPosition -> viewModelScope.launch {
                editOtherPosition(
                    event.position,
                    mainViewModel
                )
            }

            MenuEvent.DeleteSelected ->
                viewModelScope.launch {
                    selectedRecipeManager.deleteSelected(menuUIState, ::onEvent)
                }

            MenuEvent.SelectedToShopList -> viewModelScope.launch {
                selectedToShopList(
                    menuUIState,
                    ::onEvent,
                    mainViewModel,
                    selectedRecipeManager::getSelected
                )
            }

            is MenuEvent.RecipeToShopList -> viewModelScope.launch {
                recipeToShopList(
                    event.recipe,
                    mainViewModel
                )
            }

            is MenuEvent.ActionWrapperDatePicker -> menuWrapperDatePickerManager.invoke(
                event.event,
                mainViewModel,
                ::updateWeek,
                menuUIState.wrapperDatePickerUIState,
                selectedRecipeManager,
                menuUIState
            )

            is MenuEvent.FindReplaceRecipe -> viewModelScope.launch {
                findRecipeAndReplace(
                    event.recipe,
                    mainViewModel, event.context
                )
            }

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
        }
    }
}