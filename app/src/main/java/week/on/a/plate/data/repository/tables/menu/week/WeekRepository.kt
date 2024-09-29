package week.on.a.plate.data.repository.tables.menu.week

import week.on.a.plate.data.dataView.example.emptyDay
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.data.dataView.week.DayInWeekData
import week.on.a.plate.data.dataView.week.DayView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.data.repository.tables.filters.ingredient.IngredientMapper
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeDAO
import week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeMapper
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagDAO
import week.on.a.plate.data.repository.tables.filters.recipeTag.RecipeTagMapper
import week.on.a.plate.data.repository.tables.menu.day.DayDAO
import week.on.a.plate.data.repository.tables.menu.day.DayMapper
import week.on.a.plate.data.repository.tables.menu.day.DayRoom
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftDAO
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftMapper
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftRepository
import week.on.a.plate.data.repository.tables.menu.position.positionIngredient.PositionIngredientDAO
import week.on.a.plate.data.repository.tables.menu.position.positionIngredient.PositionIngredientRepository
import week.on.a.plate.data.repository.tables.menu.position.note.PositionNoteRepository
import week.on.a.plate.data.repository.tables.menu.position.positionRecipe.PositionRecipeRepository
import week.on.a.plate.data.repository.tables.menu.selection.SelectionAndRecipesInMenu
import week.on.a.plate.data.repository.tables.menu.selection.SelectionDAO
import week.on.a.plate.data.repository.tables.menu.selection.SelectionMapper
import week.on.a.plate.data.repository.tables.menu.selection.SelectionRoom
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeekRepository @Inject constructor(
    private val positionDraftRepository: PositionDraftRepository,
    private val positionDraftDAO: PositionDraftDAO,
    private val positionIngredientRepository: PositionIngredientRepository,
    private val noteRepository: PositionNoteRepository,
    private val positionRecipeRepository: PositionRecipeRepository,
    private val weekDAO: WeekDAO,
    private val dayDAO: DayDAO,
    private val selectionDAO: SelectionDAO,
    private val ingredientInRecipeDAO: IngredientInRecipeDAO,
    private val positionIngredientDAO: PositionIngredientDAO,

    //todo move to recipe module
    private val recipeTagDAO: RecipeTagDAO,
) {

    suspend fun addEmptyDay(date: LocalDate) {
        var weekId : Long? = null
        DayOfWeek.entries.forEach {
            val day = date.plusDays( (it.value - date.dayOfWeek.value).toLong() )
            val dayInRoom = dayDAO.findDay(day)
            if (dayInRoom!=null ){
                weekId = dayInRoom.weekId
            }
        }

        if (weekId == null){
            val selWeekId =
                selectionDAO.insert(SelectionRoom(0, CategoriesSelection.ForWeek.fullName))
            weekId = weekDAO.insert(WeekRoom(selWeekId))
        }

        val dayId =
            dayDAO.insert(DayRoom(date, DayInWeekData.localeDateToDayInWeekData(date), weekId!!))
        emptyDay.forEach {
            val sel = with(SelectionMapper()){
                it.viewToRoom(dayId)
            }
            selectionDAO.insert(sel)
        }
    }

    suspend fun insertNewWeek(week: WeekView) {

        val selRoom = with(SelectionMapper()) {
            week.selection.viewToRoom(0)
        }
        val selId = selectionDAO.insert(selRoom)

        val weekRoom = with(WeekMapper()) {
            week.viewToRoom(selId)
        }
        val weekId = weekDAO.insert(weekRoom)

        week.days.forEach { day ->
            val dayRoom = with(DayMapper()) {
                day.viewToRoom(weekId)
            }
            val dayId = dayDAO.insert(dayRoom)

            day.selections.forEach { selection ->

                val selectionRoom = with(SelectionMapper()) {
                    selection.viewToRoom(dayId)
                }
                val selectionId = selectionDAO.insert(selectionRoom)

                selection.positions.forEach { position ->
                    when (position) {
                        is Position.PositionDraftView -> {

                            val newId = positionDraftRepository.insert(position, selectionId)

                            //todo move to recipe module
                            position.tags.forEach {
                                val newTagId = addTag(it)
                                val tagRefs = with(PositionDraftMapper()) {
                                    genTagCrossRef(newId, newTagId)
                                }
                                positionDraftDAO.insertDraftAndTagCrossRef(tagRefs)
                            }

                            //todo move to recipe module
                            position.ingredients.forEach {
                                val newIngredientId = addIngredient(it)
                                val ingredientRefs = with(PositionDraftMapper()) {
                                    genIngredientCrossRef(newId, newIngredientId)
                                }
                                positionDraftDAO.insertDraftAndIngredientCrossRef(ingredientRefs)
                            }
                        }

                        is Position.PositionIngredientView -> {
                            positionIngredientRepository.insert(position, selectionId)

                            //todo move to recipe module
                            val idIngredient = addIngredient(position.ingredient.ingredientView)

                            val ingredientRoom = with(IngredientInRecipeMapper()) {
                                position.ingredient.viewToRoom(0, idIngredient)
                            }
                            positionIngredientDAO.insert(ingredientRoom)
                        }

                        is Position.PositionNoteView -> noteRepository.insert(position, selectionId)

                        is Position.PositionRecipeView -> positionRecipeRepository.insert(
                            position,
                            selectionId
                        )
                    }
                }
            }
        }
    }

    //todo move to recipe module
    private suspend fun addTag(recipeTagView: RecipeTagView): Long {
        val recipeTagRoom = with(RecipeTagMapper()) {
            recipeTagView.viewToRoom(0)
        }
        return recipeTagDAO.insert(recipeTagRoom)
    }

    //todo move to recipe module
    private suspend fun addIngredient(ingredientView: IngredientView): Long {
        val ingredientRoom = with(IngredientMapper()) {
            ingredientView.viewToRoom(0)
        }
        return ingredientInRecipeDAO.insert(ingredientRoom)
    }

    suspend fun getSelIdOrCreate(date: LocalDate, category: CategoriesSelection): Long {
        val day = dayDAO.findDay(date)
        if (day != null) {
            when (category) {
                CategoriesSelection.ForWeek -> {
                    val week = weekDAO.findWeek(day.weekId)
                    return week.selectionId
                }

                else -> {
                    val dayAndSelections = dayDAO.getDayAndSelection(day.dayId)
                    dayAndSelections.selections.forEach {
                        if (it.category == category.fullName) {
                            return it.selectionId
                        }
                    }
                    return selectionDAO.insert(SelectionRoom(day.dayId, category.fullName))
                }
            }
        } else {
            //create day
            val selWeekId =
                selectionDAO.insert(SelectionRoom(0, CategoriesSelection.ForWeek.fullName))
            val weekId = weekDAO.insert(WeekRoom(selWeekId))
            val dayId = dayDAO.insert(
                DayRoom(
                    date, DayInWeekData.localeDateToDayInWeekData(date), weekId
                )
            )
            return when (category) {
                CategoriesSelection.ForWeek -> selWeekId
                else -> selectionDAO.insert(SelectionRoom(dayId, category.fullName))
            }
        }

    }

    suspend fun getCurrentWeek(day: LocalDate): WeekView? {
        val today = dayDAO.findDay(day) ?: return null
        val weekAndDays = weekDAO.getWeekAndDay(today.weekId) ?: return null

        val listDaysView = mutableListOf<DayView>()
        weekAndDays.days.forEach { currentDay ->
            val dayAndSelections = dayDAO.getDayAndSelection(currentDay.dayId)

            val listSelection = mutableListOf<SelectionView>()

            dayAndSelections.selections.forEach { sel ->
                val selectionAndRecipesInMenu =
                    selectionDAO.getSelectionAndRecipesInMenu(sel.selectionId)
                val selectionView = mapSelectionB(selectionAndRecipesInMenu)
                listSelection.add(selectionView)
            }

            val dayy = with(DayMapper()) {
                currentDay.roomToView(listSelection)
            }

            listDaysView.add(dayy)
        }

        val weekSelectAndRecipesInMenu =
            selectionDAO.getSelectionAndRecipesInMenu(weekAndDays.week.selectionId)
        val weekSel = mapSelectionB(weekSelectAndRecipesInMenu)

        val weekFlow = with(WeekMapper()) { weekAndDays.week.roomToView(weekSel, listDaysView) }

        return weekFlow
    }

    private suspend fun mapSelectionB(selectionAndRecipesInMenu: SelectionAndRecipesInMenu): SelectionView {

        val listPositionRecipe =
            positionRecipeRepository.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)

        val listPositionIngredient =
            positionIngredientRepository.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)

        val listPositionNote =
            noteRepository.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)

        val listPositionDraft =
            positionDraftRepository.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)

        val targetList = mutableListOf<Position>()
        targetList.addAll(listPositionRecipe)
        targetList.addAll(listPositionIngredient)
        targetList.addAll(listPositionNote)
        targetList.addAll(listPositionDraft)

        val sel = with(SelectionMapper()) {
            selectionAndRecipesInMenu.selectionRoom.roomToView(targetList)
        }
        return sel
    }
}