package week.on.a.plate.repository.repositoriesForFeatures.menu

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.repository.tables.weekOrg.day.DayDAO
import week.on.a.plate.repository.tables.weekOrg.day.DayMapper
import week.on.a.plate.repository.tables.weekOrg.day.DayRoom
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftRepository
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientRepository
import week.on.a.plate.repository.tables.weekOrg.position.positionNote.PositionNoteRepository
import week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu.PositionRecipeRepository
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionAndRecipesInMenu
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionDAO
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionMapper
import week.on.a.plate.repository.tables.weekOrg.week.WeekDAO
import week.on.a.plate.repository.tables.weekOrg.week.WeekMapper
import week.on.a.plate.repository.tables.weekOrg.week.WeekRoom
import java.time.LocalDate
import javax.inject.Inject

class GetWeekFun @Inject constructor(
    private val positionDraftRepository: PositionDraftRepository,
    private val positionIngredientRepository: PositionIngredientRepository,
    private val noteRepository: PositionNoteRepository,
    private val positionRecipeRepository: PositionRecipeRepository,
    private val weekDAO: WeekDAO,
    private val dayDAO: DayDAO,
    private val selectionDAO: SelectionDAO,
) {

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


  /*  suspend fun getCurrentWeekFlow(day: LocalDate): Flow<WeekView> {
        val today = dayDAO.findDay(day) ?: return flowOf()
        val weekAndDays = weekDAO.getWeekAndDay(today.weekId) ?: return flowOf()

        val listDaysView = mutableListOf<Flow<DayView>>()
        weekAndDays.days.forEach { currentDay ->
            val dayAndSelections = dayDAO.getDayAndSelection(currentDay.dayId)

            val listSelection = mutableListOf<Flow<SelectionView>>()

            dayAndSelections.selections.forEach { sel ->
                val selectionAndRecipesInMenu =
                    selectionDAO.getSelectionAndRecipesInMenu(sel.selectionId)
                val selectionView = mapSelection(selectionAndRecipesInMenu)
                listSelection.add(selectionView)
            }

            val dayy = selectionsToDayVariant2(dayAndSelections.day, listSelection)
            listDaysView.add(dayy)
        }

        val weekSelectAndRecipesInMenu =
            selectionDAO.getSelectionAndRecipesInMenu(weekAndDays.week.selectionId)
        val weekSel = mapSelection(weekSelectAndRecipesInMenu)

        val weekFlow = daysToWeekVariant2(weekAndDays.week, weekSel, listDaysView)

        return weekFlow
    }

    private suspend fun selectionsToDayVariant2(
        day: DayRoom,
        selections: MutableList<Flow<SelectionView>>
    ): Flow<DayView> {
        return combine(selections) { arr ->

            val list = mutableListOf<SelectionView>()
            arr.forEach { sel ->
                list.add(sel)
            }

            val dayRoom = with(DayMapper()) {
                day.roomToView(list)
            }
            dayRoom
        }
    }

    private suspend fun daysToWeekVariant2(
        week: WeekRoom,
        sel: Flow<SelectionView>,
        days: MutableList<Flow<DayView>>
    ): Flow<WeekView> {
        val dayz = combine(days) { dayViews ->
            val list = mutableListOf<DayView>()
            dayViews.forEach { day ->
                list.add(day)
            }
            list
        }
        return combine(dayz, sel) { dayList, select ->
            with(WeekMapper()) { week.roomToView(select, dayList) }
        }
    }


    private suspend fun selectionsToDayVariant(
        day: DayRoom,
        selections: MutableList<Flow<SelectionView>>
    ): Flow<DayView> {
        return combine(selections) { arr ->
            val dayRoom = with(DayMapper()) {
                day.roomToView(arr.toMutableList())
            }
            dayRoom
        }
    }

    private suspend fun daysToWeekVariant(
        week: WeekRoom,
        sel: Flow<SelectionView>,
        days: MutableList<Flow<DayView>>
    ): Flow<WeekView> {
        val dayz = combine(days) {
            it.toMutableList()
        }
        return combine(dayz, sel) { dayList, select ->
            with(WeekMapper()) { week.roomToView(select, dayList) }
        }
    }


    private suspend fun selectionsToDay(
        day: DayRoom,
        selections: MutableList<Flow<SelectionView>>
    ): Flow<DayView> {
        var list = flowOf(mutableListOf<SelectionView>())
        selections.forEach {
            list = list.combine(it) { list, arr ->
                list.add(arr)
                list
            }
        }

        return list.transform { arr ->
            val dayRoom = with(DayMapper()) {
                day.roomToView(arr.toMutableList())
            }
            emit(dayRoom)
        }
    }

    private suspend fun daysToWeek(
        week: WeekRoom,
        sel: Flow<SelectionView>,
        days: MutableList<Flow<DayView>>
    ): Flow<WeekView> {
        var list = flowOf(mutableListOf<DayView>())
        days.forEach {
            list = list.combine(it) { list, arr ->
                list.add(arr)
                list
            }
        }

        return list.combine(sel) { listr, selr ->
            with(WeekMapper()) { week.roomToView(selr, listr) }
        }
    }*/

   /* private suspend fun mapSelection(selectionAndRecipesInMenu: SelectionAndRecipesInMenu): Flow<SelectionView> {

        val flowListPositionRecipe =
            positionRecipeRepository.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)

        val flowListPositionIngredient =
            positionIngredientRepository.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)

        val flowListPositionNote =
            noteRepository.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)

        val flowListPositionDraft =
            positionDraftRepository.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)

        return selCombineToOurSelVariant(
            flowListPositionRecipe,
            flowListPositionIngredient,
            flowListPositionNote,
            flowListPositionDraft,
            selectionAndRecipesInMenu
        )
    }*/


   /* private fun selCombineToOurSel(
        flowListPositionRecipe: Flow<List<Position>>,
        flowListPositionIngredient: Flow<List<Position>>,
        flowListPositionNote: Flow<List<Position.PositionNoteView>>,
        flowListPositionDraft: Flow<List<Position>>,
        selectionAndRecipesInMenu: SelectionAndRecipesInMenu
    ): Flow<SelectionView> {
        return combine(
            flowListPositionRecipe,
            flowListPositionIngredient,
            flowListPositionNote,
            flowListPositionDraft
        ) { arr ->
            val listPos = mutableListOf<Position>()
            arr.forEach { listPos.addAll(it) }
            with(SelectionMapper()) {
                selectionAndRecipesInMenu.selectionRoom.roomToView(listPos.toMutableList())
            }
        }
    }


    //work
    private fun selCombineToOurSelVariant(
        flowListPositionRecipe: Flow<List<Position>>,
        flowListPositionIngredient: Flow<List<Position>>,
        flowListPositionNote: Flow<List<Position.PositionNoteView>>,
        flowListPositionDraft: Flow<List<Position>>,
        selectionAndRecipesInMenu: SelectionAndRecipesInMenu
    ): Flow<SelectionView> {
        return combine(
            flowListPositionRecipe,
            flowListPositionIngredient,
            flowListPositionNote,
            flowListPositionDraft
        ) { recipes, ingredients, notes, drafts ->

            val listPos = mutableListOf<Position>()

            listPos.addAll(recipes)
            listPos.addAll(ingredients)
            listPos.addAll(notes)
            listPos.addAll(drafts)

            with(SelectionMapper()) {
                selectionAndRecipesInMenu.selectionRoom.roomToView(listPos.toMutableList())
            }
        }
    }

    //work but... doubles
    private fun selCombineToOurSelVariant2(
        flowListPositionRecipe: Flow<List<Position>>,
        flowListPositionIngredient: Flow<List<Position>>,
        flowListPositionNote: Flow<List<Position.PositionNoteView>>,
        flowListPositionDraft: Flow<List<Position>>,
        selectionAndRecipesInMenu: SelectionAndRecipesInMenu
    ): Flow<SelectionView> {
        var list = flowOf(mutableListOf<Position>())

        val listFlows = mutableListOf<Flow<List<Position>>>()
        listFlows.add(flowListPositionRecipe)
        listFlows.add(flowListPositionIngredient)
        listFlows.add(flowListPositionNote)
        listFlows.add(flowListPositionDraft)

        listFlows.forEach {
            list = list.combine(it) { list, arr ->
                list.addAll(arr)
                list
            }
        }
        return list.transform {
            val sel = with(SelectionMapper()) {
                selectionAndRecipesInMenu.selectionRoom.roomToView(it)
            }
            emit(sel)
        }
    }*/
}