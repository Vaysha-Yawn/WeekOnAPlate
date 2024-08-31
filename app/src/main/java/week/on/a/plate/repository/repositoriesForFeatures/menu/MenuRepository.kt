package week.on.a.plate.repository.repositoriesForFeatures.menu

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import week.on.a.plate.core.data.recipe.RecipeStateView
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.data.week.RecipeInMenuView
import week.on.a.plate.core.data.week.RecipeShortView
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.repository.tables.weekOrg.day.DayDAO
import week.on.a.plate.repository.tables.weekOrg.day.DayMapper
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuDAO
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuMapper
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuRoom
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionAndRecipesInMenu
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionDAO
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionMapper
import week.on.a.plate.repository.tables.weekOrg.week.WeekDAO
import week.on.a.plate.repository.tables.weekOrg.week.WeekMapper
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton


// пояснение здесь мы не добавляем рецепты, а только ссылаемся на их id значит рецепты должны быть уже доступны

@Singleton
class MenuRepository @Inject constructor(
    private val recipeInMenuDAO: RecipeInMenuDAO,
    private val weekDAO: WeekDAO,
    private val dayDAO: DayDAO,
    private val selectionDAO: SelectionDAO
) : IMenuRepository {

    override suspend fun addRecipeInMenu() {
        TODO("Not yet implemented")
    }

    override suspend fun changeRecipeInMenuState(newState: RecipeStateView) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecipeInMenu() {
        TODO("Not yet implemented")
    }

    override suspend fun changeRecipeInRecipeInMenu(newRecipe: RecipeShortView) {
        TODO("Not yet implemented")
    }

    override suspend fun changePortionsCount(newCount: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun insertNewWeek(week: WeekView) {

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

                selection.recipes.forEach { recipeInMenu ->
                    val recipeInMenuRoom = with(RecipeInMenuMapper()) {
                        recipeInMenu.viewToRoom(selectionId)
                    }
                    recipeInMenuDAO.insert(recipeInMenuRoom)
                }
            }
        }
    }

    override suspend fun getCurrentWeek(day: LocalDate): Flow<WeekView> {
        val today = dayDAO.findDay(day)?:return flowOf()
        val weekAndDays = weekDAO.getWeekAndDay(today.weekId)?:return flowOf()

        val listDaysView = mutableListOf<Flow<DayView>>()
        weekAndDays.days.forEach { currentDay ->
            val dayAndSelections = dayDAO.getDayAndSelection(currentDay.dayId)
            val listSelection = mutableListOf<Flow<SelectionView>>()
            dayAndSelections.selections.forEach { sel ->
                val selectionAndRecipesInMenu = selectionDAO.getSelectionAndRecipesInMenu(sel.selectionId)
                val selectionView = mapSelection(selectionAndRecipesInMenu)
                listSelection.add(selectionView)
            }

            var flowList = flow { emit(mutableListOf<SelectionView>()) }
            listSelection.forEach { flow->
                flowList = flowList.combine(flow){ f1, f2->
                    f1.add(f2)
                    f1
                }
            }

            val newDayFlow = flowList.transform<MutableList<SelectionView>, DayView> { listSel->
                val newDay = with(DayMapper()) {
                    dayAndSelections.day.roomToView(listSel)
                }
                emit(newDay)
            }

            listDaysView.add(newDayFlow)
        }

        var flowListDay = flow { emit(mutableListOf<DayView>()) }
        listDaysView.forEach { flow->
            flowListDay = flowListDay.combine(flow){ f1, f2->
                f1.add(f2)
                f1
            }
        }

        val weekSelectAndRecipesInMenu = selectionDAO.getSelectionAndRecipesInMenu(weekAndDays.week.selectionId)
        val weekSel = mapSelection(weekSelectAndRecipesInMenu)

        val weekFlow = weekSel.combine(flowListDay){sel, listDay->
            with(WeekMapper()) { weekAndDays.week.roomToView(sel, listDay) }
        }

        return weekFlow
    }


    private suspend fun mapSelection(selectionAndRecipesInMenu:SelectionAndRecipesInMenu): Flow<SelectionView> {
        val t = recipeInMenuDAO.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId).transform<List<RecipeInMenuRoom>,SelectionView> {
            val list = mutableListOf<RecipeInMenuView>()
            it.forEach { recipeInMenu ->
            with(RecipeInMenuMapper()) {
                val newRecipeInMenuView =
                    recipeInMenu.roomToView(
                        recipeInMenu.recipeId, recipeInMenu.recipeName
                    )
                list.add(newRecipeInMenuView)
            }
            }
            val sel = selectionAndRecipesInMenu.selectionRoom
            val newSel = with(SelectionMapper()) {
                sel.roomToView(list)
            }
            emit(newSel)
        }
        return t
    }
}