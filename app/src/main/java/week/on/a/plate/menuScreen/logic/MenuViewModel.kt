package week.on.a.plate.menuScreen.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.MainViewModel
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.core.dialogs.menu.addPosition.event.AddPositionEvent
import week.on.a.plate.core.dialogs.menu.addPosition.logic.AddPositionViewModel
import week.on.a.plate.core.dialogs.menu.changePositionCount.logic.ChangePortionsCountViewModel
import week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.core.dialogs.menu.editNote.logic.EditNoteViewModel
import week.on.a.plate.core.dialogs.menu.editOtherPosition.event.EditOtherPositionEvent
import week.on.a.plate.core.dialogs.menu.editOtherPosition.logic.EditOtherPositionViewModel
import week.on.a.plate.core.dialogs.menu.editPositionIngredient.logic.EditPositionIngredientViewModel
import week.on.a.plate.core.dialogs.menu.editRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.core.dialogs.menu.editRecipePosition.logic.EditRecipePositionViewModel
import week.on.a.plate.core.fullScereenDialog.filters.navigation.FilterDestination
import week.on.a.plate.core.navigation.bottomBar.SearchScreen
import week.on.a.plate.menuScreen.event.ActionWeekMenuDB
import week.on.a.plate.menuScreen.event.MenuEvent
import week.on.a.plate.menuScreen.event.NavFromMenuData
import week.on.a.plate.menuScreen.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.menuScreen.logic.useCase.NavigationMenu
import week.on.a.plate.menuScreen.logic.useCase.SelectedRecipeManager
import week.on.a.plate.menuScreen.state.MenuIUState
import week.on.a.plate.menuScreen.state.WeekState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val selectedRecipeManager: SelectedRecipeManager,
    val navigationMenu: NavigationMenu,
) : ViewModel() {

    private var activeDay = LocalDate.of(2024, 8, 28)

    //LocalDate.now()
    lateinit var mainViewModel: MainViewModel


    val weekState: MutableStateFlow<WeekState> = MutableStateFlow(WeekState.Loading)
    val menuUIState = MenuIUState.MenuIUStateExample

    init {
        viewModelScope.launch {
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
                event.selectedEvent,
                menuUIState
            )

            is MenuEvent.GetSelIdAndCreate -> getSelAndCreate()
            is MenuEvent.ChangeWeek -> {
                activeDay = event.date
                updateWeek()
            }

            MenuEvent.ChooseWeek -> chooseWeek()
            is MenuEvent.CreatePosition -> addPosition(event.selId)
            is MenuEvent.EditPosition -> {
                when (event.position) {
                    is Position.PositionRecipeView -> {
                        editPositionRecipe(event.position)
                    }

                    else -> {
                        editOtherPosition(event.position)
                    }
                }
            }

            is MenuEvent.RecipeToShopList -> TODO()
            MenuEvent.SelectedToShopList -> TODO()

            is MenuEvent.FindReplaceRecipe -> findReplaceRecipe(event.recipe)
            is MenuEvent.NavToAddRecipe -> navToAddRecipe(event.selId)
            is MenuEvent.SearchByDraft -> searchByDraft(event.draft)
        }
    }

    private fun searchByDraft(draft: Position.PositionDraftView) {
        //todo after delete and add this
        mainViewModel.searchViewModel.launchAndGet(
            draft.selectionId,
            Pair(draft.tags, draft.ingredients)
        )
        mainViewModel.nav.navigate(SearchScreen)
    }

    private fun navToAddRecipe(selId: Long?) {
        mainViewModel.searchViewModel.launchAndGet(selId, null)
        mainViewModel.nav.navigate(SearchScreen)
    }

    private fun findReplaceRecipe(recipe: Position.PositionRecipeView) {
        //todo after delete and add this
        mainViewModel.searchViewModel.launchAndGet(recipe.selectionId, null)
        mainViewModel.nav.navigate(SearchScreen)
    }

    private fun getSelAndCreate() {
        specifyDate { long ->
            addPosition(long)
        }
    }

    private fun specifyDate(use: (Long) -> Unit) {
        viewModelScope.launch {
            val vm = mainViewModel.specifySelectionViewModel
            navigationMenu.onEvent(NavFromMenuData.SpecifySelection)
            vm.launchAndGet(use)
        }
    }

    private fun addPosition(selId: Long) {
        viewModelScope.launch {
            val vm = AddPositionViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet() { event ->
                when (event) {
                    AddPositionEvent.AddDraft -> addDraft(selId)
                    AddPositionEvent.AddIngredient -> addIngredientPosition(selId)
                    AddPositionEvent.AddNote -> addNote(selId)
                    AddPositionEvent.AddRecipe -> onEvent(MenuEvent.NavToAddRecipe(selId))
                    AddPositionEvent.Close -> {}
                }
            }
        }
    }

    private fun addDraft(selId: Long) {
        viewModelScope.launch {
            val vm = mainViewModel.filterViewModel
            vm.mainViewModel.nav.navigate(FilterDestination)
            vm.launchAndGet(null) { filters ->
                if (filters.first.isEmpty() && filters.second.isEmpty()) return@launchAndGet
                val draft = Position.PositionDraftView(0, filters.first, filters.second, selId)
                onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.AddDraft(draft)))
            }
        }
    }

    private fun editDraft(oldDraft: Position.PositionDraftView) {
        viewModelScope.launch {
            val vm = mainViewModel.filterViewModel
            vm.mainViewModel.nav.navigate(FilterDestination)
            vm.launchAndGet(Pair(oldDraft.tags, oldDraft.ingredients)) { filters ->
                if (filters.first.isEmpty() && filters.second.isEmpty()) {
                    onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.Delete(oldDraft)))
                } else {
                    onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.EditDraft(oldDraft, filters)))
                }
            }
        }
    }

    private fun editOtherPosition(position: Position) {
        viewModelScope.launch {
            val vm = EditOtherPositionViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet() { event ->
                when (event) {
                    EditOtherPositionEvent.Close -> {}
                    EditOtherPositionEvent.Delete -> onEvent(
                        MenuEvent.ActionDBMenu(
                            ActionWeekMenuDB.Delete(
                                position
                            )
                        )
                    )

                    EditOtherPositionEvent.Double -> getSelAndDouble(position)
                    EditOtherPositionEvent.Edit -> {
                        when (position) {
                            is Position.PositionDraftView -> editDraft(position)
                            is Position.PositionIngredientView -> editIngredientPosition(position)
                            is Position.PositionNoteView -> editNote(position)
                            is Position.PositionRecipeView -> {}
                        }
                    }

                    EditOtherPositionEvent.Move -> getSelAndMove(position)
                }
            }
        }
    }

    private fun getSelAndMove(position: Position) {
        specifyDate { selId ->
            onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.MovePositionInMenuDB(selId, position)))
        }
    }

    private fun getSelAndDouble(position: Position) {
        specifyDate { selId ->
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionWeekMenuDB.DoublePositionInMenuDB(
                        selId,
                        position
                    )
                )
            )
        }
    }

    private fun editPositionRecipe(position: Position.PositionRecipeView) {
        viewModelScope.launch {
            val vm = EditRecipePositionViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet() { event ->
                when (event) {
                    EditRecipePositionEvent.AddToCart -> TODO()
                    EditRecipePositionEvent.ChangePotionsCount -> changePortionsCount(position)
                    EditRecipePositionEvent.Delete -> onEvent(
                        MenuEvent.ActionDBMenu(
                            ActionWeekMenuDB.Delete(position)
                        )
                    )

                    EditRecipePositionEvent.Double -> getSelAndDouble(position)
                    EditRecipePositionEvent.FindReplace -> onEvent(
                        MenuEvent.FindReplaceRecipe(
                            position
                        )
                    )

                    EditRecipePositionEvent.Move -> getSelAndMove(position)
                    EditRecipePositionEvent.Close -> {}
                }
            }
        }
    }

    private fun addIngredientPosition(selId: Long) {
        viewModelScope.launch {
            val vm = EditPositionIngredientViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(null) { updatedIngredient ->
                onEvent(
                    MenuEvent.ActionDBMenu(
                        ActionWeekMenuDB.AddIngredientPositionDB(
                            updatedIngredient,
                            selId
                        )
                    )
                )
            }
        }
    }

    private fun editIngredientPosition(ingredientPos: Position.PositionIngredientView) {
        viewModelScope.launch {
            val vm = EditPositionIngredientViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(ingredientPos) { updatedIngredient ->
                onEvent(
                    MenuEvent.ActionDBMenu(
                        ActionWeekMenuDB.EditIngredientPositionDB(
                            updatedIngredient
                        )
                    )
                )
            }
        }
    }

    private fun changePortionsCount(recipe: Position.PositionRecipeView) {
        viewModelScope.launch {
            val vm = ChangePortionsCountViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(recipe.portionsCount) { portionsCount ->
                onEvent(
                    MenuEvent.ActionDBMenu(
                        ActionWeekMenuDB.ChangePortionsCountDB(
                            recipe,
                            portionsCount
                        )
                    )
                )
            }
        }
    }

    private fun editNote(note: Position.PositionNoteView) {
        viewModelScope.launch {
            val vm = EditNoteViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(note) { updatedNote ->
                onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.EditNoteDB(updatedNote)))
            }
        }
    }

    private fun addNote(selId: Long) {
        viewModelScope.launch {
            val vm = EditNoteViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(null) { updatedNote ->
                onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.AddNoteDB(updatedNote.note, selId)))
            }
        }
    }

    private fun chooseWeek() {
        viewModelScope.launch {
            val vm = ChooseWeekViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet() { date ->
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
}