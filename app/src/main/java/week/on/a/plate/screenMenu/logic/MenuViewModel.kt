package week.on.a.plate.screenMenu.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.navigation.SearchScreen
import week.on.a.plate.data.dataView.example.WeekDataExample
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenFilters.navigation.FilterDestination
import week.on.a.plate.screenFilters.state.FilterMode
import week.on.a.plate.dialogAddPosition.event.AddPositionEvent
import week.on.a.plate.dialogAddPosition.logic.AddPositionViewModel
import week.on.a.plate.dialogChangePositionCount.logic.ChangePortionsCountViewModel
import week.on.a.plate.dialogChooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.dialogEditNote.logic.EditNoteViewModel
import week.on.a.plate.dialogEditOtherPosition.event.EditOtherPositionEvent
import week.on.a.plate.dialogEditOtherPosition.logic.EditOtherPositionViewModel
import week.on.a.plate.dialogEditPositionIngredient.logic.EditPositionIngredientViewModel
import week.on.a.plate.dialogEditRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.dialogEditRecipePosition.logic.EditRecipePositionViewModel
import week.on.a.plate.screenMenu.event.ActionWeekMenuDB
import week.on.a.plate.screenMenu.event.MenuEvent
import week.on.a.plate.screenMenu.event.NavFromMenuData
import week.on.a.plate.screenMenu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screenMenu.logic.useCase.SelectedRecipeManager
import week.on.a.plate.screenMenu.state.MenuIUState
import week.on.a.plate.screenMenu.state.WeekState
import week.on.a.plate.screenRecipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screenSpecifySelection.navigation.SpecifySelectionDirection
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val selectedRecipeManager: SelectedRecipeManager,
) : ViewModel() {
    lateinit var navController: NavHostController
    private var activeDay = LocalDate.now()
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
            val week = sCRUDRecipeInMenu.menuR.getCurrentWeek(activeDay, Locale.getDefault())
            weekState.value = WeekState.Success(week)
            menuUIState.titleTopBar.value = getTitle(week)
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
                when (event.navData) {
                    is NavFromMenuData.SpecifySelection -> {
                        navController.navigate(SpecifySelectionDirection)
                    }

                    is NavFromMenuData.NavToFullRecipe -> {
                        mainViewModel.recipeDetailsViewModel.launch(
                            event.navData.recId,
                            event.navData.portionsCount
                        )
                        navController.navigate(RecipeDetailsDestination)
                        viewModelScope.launch {
                            delay(600L)
                            mainViewModel.recipeDetailsViewModel.updIngredientsCount()
                        }
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
            MenuEvent.DeleteSelected -> {
                selectedRecipeManager.getSelected(menuUIState).forEach {
                    onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.Delete(it)))
                }
            }
            is MenuEvent.CreateFirstNonPosedPosition -> {
                viewModelScope.launch {
                    val sel = sCRUDRecipeInMenu.menuR.getSelIdOrCreate(event.date, false, CategoriesSelection.NonPosed, mainViewModel.locale)
                    onEvent(MenuEvent.CreatePosition(sel))
                }
            }
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
        specifyDate { selId, count ->
            addPosition(selId)
        }
    }

    private fun specifyDate(use: (Long, Int) -> Unit) {
        viewModelScope.launch {
            val vm = mainViewModel.specifySelectionViewModel
            onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.SpecifySelection))
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
            vm.launchAndGet(FilterMode.All, null) { filters ->
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
            vm.launchAndGet(FilterMode.All, Pair(oldDraft.tags, oldDraft.ingredients)) { filters ->
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
        specifyDate { selId, count ->
            onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.MovePositionInMenuDB(selId, position)))
        }
    }

    private fun getSelAndDouble(position: Position) {
        specifyDate { selId, count ->
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