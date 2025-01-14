package week.on.a.plate.screens.menu.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.getTitleWeek
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.MenuEvent
import week.on.a.plate.screens.menu.event.NavFromMenuData
import week.on.a.plate.screens.menu.logic.useCase.AddPositionUseCase
import week.on.a.plate.screens.menu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screens.menu.logic.useCase.CreateFirstNonPosedPositionUseCase
import week.on.a.plate.screens.menu.logic.useCase.EditOtherPositionUseCase
import week.on.a.plate.screens.menu.logic.useCase.FindRecipeAndReplaceUseCase
import week.on.a.plate.screens.menu.logic.useCase.GetSelAndUseCase
import week.on.a.plate.screens.menu.logic.useCase.MenuWrapperDatePickerManager
import week.on.a.plate.screens.menu.logic.useCase.OtherPositionActionsMore
import week.on.a.plate.screens.menu.logic.useCase.RecipePositionActionsMore
import week.on.a.plate.screens.menu.logic.useCase.RecipeToShopListUseCase
import week.on.a.plate.screens.menu.logic.useCase.SearchByDraftUseCase
import week.on.a.plate.screens.menu.logic.useCase.SelectedRecipeManager
import week.on.a.plate.screens.menu.logic.useCase.SelectedToShopListUseCase
import week.on.a.plate.screens.menu.logic.useCase.SelectionUseCase
import week.on.a.plate.screens.menu.state.MenuUIState
import week.on.a.plate.screens.specifySelection.navigation.SpecifySelectionDestination
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MenuViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val selectedRecipeManager: SelectedRecipeManager,
    private val menuWrapperDatePickerManager: MenuWrapperDatePickerManager,
    private val getSelAndUse: GetSelAndUseCase,
    private val recipePositionActionsMore: RecipePositionActionsMore,
    private val otherPositionActionsMore: OtherPositionActionsMore,
    private val editOtherPosition: EditOtherPositionUseCase,
    private val addPosition: AddPositionUseCase,
    private val selectedToShopList: SelectedToShopListUseCase,
    private val recipeToShopList: RecipeToShopListUseCase,
    private val findRecipeAndReplace: FindRecipeAndReplaceUseCase,
    private val searchByDraft: SearchByDraftUseCase,
    private val createFirstNonPosedPosition: CreateFirstNonPosedPositionUseCase,
) : ViewModel() {

    lateinit var mainViewModel: MainViewModel
    val menuUIState = MenuUIState.MenuUIStateExample
    private val activeDay = menuUIState.wrapperDatePickerUIState.activeDay
    private lateinit var selectionUseCase: SelectionUseCase

    init {
        viewModelScope.launch {
            updateWeek()
            menuUIState.wrapperDatePickerUIState.activeDayInd.value =
                menuUIState.wrapperDatePickerUIState.activeDay.value.dayOfWeek.ordinal
        }
    }

    fun initWithMainVM(mainViewModel: MainViewModel) {
        selectionUseCase = SelectionUseCase(
            mainViewModel,
            viewModelScope,
            ::updateWeek,
            sCRUDRecipeInMenu,
            activeDay,
            addPosition
        )
    }

    fun updateWeek() {
        viewModelScope.launch {
            val week = sCRUDRecipeInMenu.menuR.getCurrentWeek(
                menuUIState.nonPosedFullName.value,
                menuUIState.forWeekFullName.value,
                activeDay.value,
                Locale.getDefault()
            )
            week.days.forEach { day ->
                day.selections = day.selections.sortedBy { it.dateTime }
            }
            menuUIState.wrapperDatePickerUIState.titleTopBar.value =
                getTitleWeek(week.days[0].date, week.days[6].date)
            menuUIState.week.value = week
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
                    is NavFromMenuData.SpecifySelection -> mainViewModel.nav.navigate(
                        SpecifySelectionDestination
                    )

                    is NavFromMenuData.NavToFullRecipe -> {
                        mainViewModel.recipeDetailsViewModel.launch(
                            event.navData.recId,
                            event.navData.portionsCount
                        )
                    }
                }
            }

            is MenuEvent.ActionDBMenu -> {
                viewModelScope.launch {
                    sCRUDRecipeInMenu.onEvent(
                        event.actionWeekMenuDB
                    )
                    updateWeek()
                }
            }

            is MenuEvent.ActionSelect -> selectedRecipeManager.onEvent(
                event.selectedEvent,
                menuUIState
            )

            is MenuEvent.GetSelIdAndCreate -> getSelAndUse.getSelAndCreate(
                event.context,
                mainViewModel,
                ::onEvent
            )

            is MenuEvent.CreatePosition -> viewModelScope.launch {
                addPosition(
                    event.selId,
                    event.context,
                    mainViewModel,
                    viewModelScope,
                    ::onEvent,
                    ::updateWeek
                )
            }

            is MenuEvent.EditPositionMore -> {
                viewModelScope.launch {
                    when (event.position) {
                        is Position.PositionRecipeView -> recipePositionActionsMore(
                            event.position,
                            mainViewModel,
                            ::onEvent
                        )

                        else -> otherPositionActionsMore(
                            event.position,
                            mainViewModel,
                            ::onEvent,
                            mainViewModel::onEvent
                        )
                    }
                }
            }

            is MenuEvent.EditOtherPosition -> viewModelScope.launch {
                editOtherPosition(
                    event.position,
                    mainViewModel,
                    ::onEvent
                )
            }

            MenuEvent.DeleteSelected -> selectedRecipeManager.deleteSelected(menuUIState, ::onEvent)
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
                activeDay,
                selectedRecipeManager,
                menuUIState
            )

            is MenuEvent.FindReplaceRecipe -> viewModelScope.launch {
                findRecipeAndReplace(
                    event.recipe,
                    mainViewModel,
                    ::updateWeek,
                    ::onEvent,
                )
            }

            is MenuEvent.SearchByDraft -> viewModelScope.launch {
                searchByDraft(
                    event.draft, mainViewModel,
                    ::updateWeek,
                    ::onEvent,
                )
            }

            is MenuEvent.CreateFirstNonPosedPosition -> viewModelScope.launch {
                createFirstNonPosedPosition(
                    event.date,
                    event.selectionView,
                    event.context, mainViewModel, ::onEvent
                )
            }

            is MenuEvent.EditOrDeleteSelection -> selectionUseCase.editOrDeleteSelection(event.sel)
            is MenuEvent.CreateSelection ->
                selectionUseCase.createSelection(event.date, event.isForWeek)

            is MenuEvent.CreateWeekSelIdAndCreatePosition ->
                selectionUseCase.createWeekSelIdAndCreatePosition(event.context, ::onEvent)
        }
    }
}