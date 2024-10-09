package week.on.a.plate.screenMenu.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.navigation.SearchScreen
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.RecipeShortView
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.getTitle
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.dialogAddPosition.event.AddPositionEvent
import week.on.a.plate.dialogAddPosition.logic.AddPositionViewModel
import week.on.a.plate.dialogChangePositionCount.logic.ChangePortionsCountViewModel
import week.on.a.plate.dialogChooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.dialogEditOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogEditOneString.state.EditOneStringUIState
import week.on.a.plate.dialogEditOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.dialogEditOrDelete.logic.EditOrDeleteViewModel
import week.on.a.plate.dialogEditOtherPosition.event.EditOtherPositionEvent
import week.on.a.plate.dialogEditOtherPosition.logic.EditOtherPositionViewModel
import week.on.a.plate.dialogEditPositionIngredient.logic.EditPositionIngredientViewModel
import week.on.a.plate.dialogEditRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.dialogEditRecipePosition.logic.EditRecipePositionViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenFilters.navigation.FilterDestination
import week.on.a.plate.screenFilters.state.FilterEnum
import week.on.a.plate.screenFilters.state.FilterMode
import week.on.a.plate.screenInventory.navigation.InventoryDirection
import week.on.a.plate.screenMenu.event.ActionWeekMenuDB
import week.on.a.plate.screenMenu.event.MenuEvent
import week.on.a.plate.screenMenu.event.NavFromMenuData
import week.on.a.plate.screenMenu.logic.useCase.CRUDRecipeInMenu
import week.on.a.plate.screenMenu.logic.useCase.SelectedRecipeManager
import week.on.a.plate.screenMenu.state.MenuIUState
import week.on.a.plate.screenMenu.state.WeekState
import week.on.a.plate.screenRecipeDetails.navigation.RecipeDetailsDestination
import week.on.a.plate.screenSpecifySelection.logic.SpecifySelectionResult
import week.on.a.plate.screenSpecifySelection.navigation.SpecifySelectionDirection
import java.time.LocalDate
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val selectedRecipeManager: SelectedRecipeManager,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    private var activeDay = LocalDate.now()
    lateinit var mainViewModel: MainViewModel

    val weekState: MutableStateFlow<WeekState> = MutableStateFlow(WeekState.Loading)
    val menuUIState = MenuIUState.MenuIUStateExample

    init {
        viewModelScope.launch {
            updateWeek()
            menuUIState.activeDayInd.value = activeDay.dayOfWeek.ordinal
        }
    }

    fun updateWeek() {
        viewModelScope.launch {
            val week = sCRUDRecipeInMenu.menuR.getCurrentWeek(activeDay, Locale.getDefault())
            weekState.value = WeekState.Success(week)
            menuUIState.titleTopBar.value = week.getTitle()
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> mainViewModel.onEvent(event)
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
                    is NavFromMenuData.SpecifySelection -> mainViewModel.nav.navigate(
                        SpecifySelectionDirection
                    )

                    is NavFromMenuData.NavToFullRecipe -> navToFullRecipe(event.navData)
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
                menuUIState.activeDayInd.value = event.date.dayOfWeek.ordinal
                updateWeek()
            }

            MenuEvent.ChooseWeek -> chooseWeek()
            is MenuEvent.CreatePosition -> addPosition(event.selId)
            is MenuEvent.EditPosition -> {
                when (event.position) {
                    is Position.PositionRecipeView -> editPositionRecipe(event.position)
                    else -> editOtherPosition(event.position)
                }
            }

            is MenuEvent.RecipeToShopList -> recipeToShopList(event.recipe)
            MenuEvent.SelectedToShopList -> selectedToShopList()
            is MenuEvent.FindReplaceRecipe -> findReplaceRecipe(event.recipe)
            is MenuEvent.NavToAddRecipe -> navToAddRecipe(event.selId)
            is MenuEvent.SearchByDraft -> searchByDraft(event.draft)
            MenuEvent.DeleteSelected -> deleteSelected()
            is MenuEvent.CreateFirstNonPosedPosition -> createFirstNonPosedPosition(event.date, event.name)
            is MenuEvent.EditOrDeleteSelection -> editOrDeleteSelection(event.sel)
            is MenuEvent.CreateSelection -> createSelection(event.date, event.isForWeek)
            MenuEvent.CreateWeekSelIdAndCreatePosition -> createWeekSelIdAndCreatePosition()
        }
    }

    private fun createWeekSelIdAndCreatePosition() {
        viewModelScope.launch {
            val id = sCRUDRecipeInMenu.menuR.getSelIdOrCreate(activeDay, true, CategoriesSelection.ForWeek.fullName, mainViewModel.locale)
            addPosition(id)
        }
    }

    private fun createSelection(date: LocalDate, isForWeek: Boolean) {
        val vmCategory = EditOneStringViewModel()
        vmCategory.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vmCategory))
        viewModelScope.launch {
            vmCategory.launchAndGet(EditOneStringUIState(startTitle = "Добавить приём пищи", startPlaceholder = "Завтрак...")) { newName ->
                sCRUDRecipeInMenu.onEvent(
                    ActionWeekMenuDB.CreateSelection(
                        date,
                        newName, mainViewModel.locale, isForWeek
                    )
                )
                updateWeek()
            }
        }
    }

    private fun editOrDeleteSelection(sel: SelectionView) {
        val vm = EditOrDeleteViewModel()
        vm.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        viewModelScope.launch {
            vm.launchAndGet { event ->
                when (event) {
                    EditOrDeleteEvent.Close -> {}
                    EditOrDeleteEvent.Delete -> deleteSelection(sel)
                    EditOrDeleteEvent.Edit -> editSelection(sel)
                }
            }
        }
    }

    private suspend fun editSelection(sel: SelectionView) {
        val vmCategory = EditOneStringViewModel()
        vmCategory.mainViewModel = mainViewModel
        mainViewModel.onEvent(MainEvent.OpenDialog(vmCategory))
        vmCategory.launchAndGet(EditOneStringUIState(sel.name,  startTitle = "Изменение названия приёма пищи",
             startPlaceholder = "Завтрак...")) { newName ->
            sCRUDRecipeInMenu.onEvent(
                ActionWeekMenuDB.EditSelection(
                    sel,
                    newName
                )
            )
            updateWeek()
        }
    }


    private suspend fun deleteSelection(sel: SelectionView) {
        sCRUDRecipeInMenu.onEvent(
            ActionWeekMenuDB.DeleteSelection(
                sel
            )
        )
        updateWeek()
    }

    private fun navToFullRecipe(navData: NavFromMenuData.NavToFullRecipe) {
        mainViewModel.recipeDetailsViewModel.launch(
            navData.recId,
            navData.portionsCount
        )
        viewModelScope.launch {
            delay(600L)
            mainViewModel.recipeDetailsViewModel.updIngredientsCount()
            mainViewModel.nav.navigate(RecipeDetailsDestination)
        }
    }

    private fun deleteSelected() {
        selectedRecipeManager.getSelected(menuUIState).forEach {
            onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.Delete(it)))
        }
        switchEditMode()
    }

    private fun createFirstNonPosedPosition(date: LocalDate, name: String) {
        viewModelScope.launch {
            val sel = sCRUDRecipeInMenu.menuR.getSelIdOrCreate(
                date,
                false,
                name,
                mainViewModel.locale
            )
            onEvent(MenuEvent.CreatePosition(sel))
        }
    }

    private fun recipeToShopList(positionRecipeView: Position.PositionRecipeView) {
        viewModelScope.launch {
            val currentPortions = positionRecipeView.portionsCount
            val recipe = recipeRepository.getRecipe(positionRecipeView.recipe.id)
            val standardCount = recipe.standardPortionsCount
            val ingredients = recipe.ingredients.map { ingredientInRecipeView ->
                ingredientInRecipeView.count =
                    ingredientInRecipeView.count / standardCount * currentPortions
                ingredientInRecipeView
            }
            mainViewModel.inventoryViewModel.launchAndGet(ingredients)
            mainViewModel.nav.navigate(InventoryDirection)
        }
    }

    private fun selectedToShopList() {
        viewModelScope.launch {
            switchEditMode()
            val selected = selectedRecipeManager.getSelected(menuUIState)
            val list = selected.flatMap { positionRecipeView ->
                val currentPortions = positionRecipeView.portionsCount
                val recipe = recipeRepository.getRecipe(positionRecipeView.recipe.id)
                val standardCount = recipe.standardPortionsCount
                recipe.ingredients.map { ingredientInRecipeView ->
                    ingredientInRecipeView.count =
                        ingredientInRecipeView.count / standardCount * currentPortions
                    ingredientInRecipeView
                }
            }
            mainViewModel.inventoryViewModel.launchAndGetMore(list)
            mainViewModel.nav.navigate(InventoryDirection)
        }
    }

    private fun searchByDraft(draft: Position.PositionDraftView) {
        viewModelScope.launch {
            mainViewModel.searchViewModel.launchAndGet(
                draft.selectionId,
                Pair(draft.tags, draft.ingredients)
            ) { recipe ->
                onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.Delete(draft)))
                val recipePosition = Position.PositionRecipeView(
                    0,
                    RecipeShortView(recipe.id, recipe.name,  recipe.img),
                    2,
                    draft.selectionId
                )
                sCRUDRecipeInMenu.onEvent(
                    ActionWeekMenuDB.AddRecipePositionInMenuDB(
                        draft.selectionId,
                        recipePosition
                    )
                )
            }
        }
        mainViewModel.nav.navigate(SearchScreen)
    }

    private fun navToAddRecipe(selId: Long) {
        viewModelScope.launch {
            mainViewModel.nav.navigate(SearchScreen)
            mainViewModel.searchViewModel.launchAndGet(selId, null) { recipe ->
                val recipePosition = Position.PositionRecipeView(
                    0,
                    RecipeShortView(recipe.id, recipe.name, recipe.img),
                    2,
                    selId
                )
                sCRUDRecipeInMenu.onEvent(
                    ActionWeekMenuDB.AddRecipePositionInMenuDB(
                        selId,
                        recipePosition
                    )
                )
            }
        }
    }

    private fun findReplaceRecipe(positionRecipe: Position.PositionRecipeView) {
        viewModelScope.launch {
            mainViewModel.searchViewModel.launchAndGet(positionRecipe.selectionId, null) { recipe ->
                val recipePosition = Position.PositionRecipeView(
                    0,
                    RecipeShortView(recipe.id, recipe.name, recipe.img),
                    2,
                    positionRecipe.selectionId
                )
                sCRUDRecipeInMenu.onEvent(
                    ActionWeekMenuDB.AddRecipePositionInMenuDB(
                        positionRecipe.selectionId,
                        recipePosition
                    )
                )
            }
            mainViewModel.nav.navigate(SearchScreen)
        }
    }

    private fun getSelAndCreate() {
        specifyDate { res ->
            addPosition(res.selId)
        }
    }

    private fun specifyDate(use: (SpecifySelectionResult) -> Unit) {
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
            vm.launchAndGet(FilterMode.Multiple, FilterEnum.IngredientAndTag,null, false) { filters ->
                if (filters.tags?.isEmpty()==true && filters.ingredients?.isEmpty()==true) return@launchAndGet
                val draft = Position.PositionDraftView(0, filters.tags!!, filters.ingredients!!, selId)
                onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.AddDraft(draft)))
            }
        }
    }

    private fun editDraft(oldDraft: Position.PositionDraftView) {
        viewModelScope.launch {
            val vm = mainViewModel.filterViewModel
            vm.mainViewModel.nav.navigate(FilterDestination)
            vm.launchAndGet(FilterMode.Multiple,FilterEnum.IngredientAndTag, Pair(oldDraft.tags, oldDraft.ingredients), false) { filters ->
                if (filters.tags?.isEmpty()==true && filters.ingredients?.isEmpty()==true || filters.ingredients==null || filters.tags==null ) {
                    onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.Delete(oldDraft)))
                } else {
                    onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.EditDraft(oldDraft, Pair(filters.tags, filters.ingredients))))
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
        specifyDate { res ->
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionWeekMenuDB.MovePositionInMenuDB(
                        res.selId,
                        position
                    )
                )
            )
        }
    }

    private fun getSelAndDouble(position: Position) {
        specifyDate { res ->
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionWeekMenuDB.DoublePositionInMenuDB(
                        res.selId,
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
                    EditRecipePositionEvent.AddToCart -> onEvent(MenuEvent.RecipeToShopList(position))
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
            vm.launchAndGet(null, true) { updatedIngredient ->
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
            vm.launchAndGet(ingredientPos, false) { updatedIngredient ->
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
            val vm = EditOneStringViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(EditOneStringUIState(note.note, "Изменение заметки", "Введите текст заметки здесь...")) { updatedNote ->
                onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.EditNoteDB(Position.PositionNoteView(note.id, updatedNote, note.selectionId))))
            }
        }
    }

    private fun addNote(selId: Long) {
        viewModelScope.launch {
            val vm = EditOneStringViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(EditOneStringUIState("", "Добавить заметку", "Введите текст заметки здесь...")) { updatedNote ->
                onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.AddNoteDB(updatedNote, selId)))
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

    private fun switchWeekOrDayView() {
        menuUIState.itsDayMenu.value = !menuUIState.itsDayMenu.value
        selectedRecipeManager.clear(menuUIState)
    }

    private fun switchEditMode() {
        menuUIState.editing.value = !menuUIState.editing.value
    }
}