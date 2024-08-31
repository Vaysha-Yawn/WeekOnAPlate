package week.on.a.plate.repository.repositoriesForFeatures.menu

import kotlinx.coroutines.ExperimentalCoroutinesApi
import week.on.a.plate.core.data.recipe.RecipeStateView
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.data.week.RecipeInMenuView
import week.on.a.plate.core.data.week.RecipeShortView
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.repository.tables.weekOrg.day.DayAndSelections
import week.on.a.plate.repository.tables.weekOrg.day.DayDAO
import week.on.a.plate.repository.tables.weekOrg.day.DayMapper
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuDAO
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuMapper
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

    override suspend fun getCurrentWeek(day: LocalDate): WeekView {
        val today = dayDAO.findDay(day)
        val weekAndDays = weekDAO.getWeekAndDay(today.weekId)
        val mapDaysSelections = mutableMapOf<Long, DayAndSelections>()
        weekAndDays.days.forEach { day ->
            mapDaysSelections[day.dayId] = dayDAO.getDayAndSelection(day.dayId)
        }

        val mapSelectionAndRecipesInMenu =
            mutableMapOf<Long, SelectionAndRecipesInMenu>()
        mapDaysSelections.values.forEach { dayAndSel ->
            dayAndSel.selections.forEach { sel ->
                mapSelectionAndRecipesInMenu[sel.selectionId] =
                    selectionDAO.getSelectionAndRecipesInMenu(sel.selectionId)
            }
        }

        val weekSelect =
            selectionDAO.getSelectionAndRecipesInMenu(weekAndDays.week.selectionId)
        mapSelectionAndRecipesInMenu[weekSelect.selectionRoom.selectionId] = weekSelect


        //second

        val mapSelectionsView = mutableMapOf<Long, MutableList<SelectionView>>()
        mapSelectionAndRecipesInMenu.values.forEach { selectionAndRecipesInMenu ->
            val list = mutableListOf<RecipeInMenuView>()
            selectionAndRecipesInMenu.recipeInMenuRooms.forEach { recipeInMenu ->
                with(RecipeInMenuMapper()) {
                    val newRecipeInMenuView =
                        recipeInMenu.roomToView(
                            recipeInMenu.recipeId, recipeInMenu.recipeName
                        )
                    list.add(newRecipeInMenuView)
                }
            }
            val sel = selectionAndRecipesInMenu.selectionRoom
            with(SelectionMapper()) {
                val newSel =
                    sel.roomToView(list)
                val dayId = sel.dayId

                if (mapSelectionsView[dayId] == null) {
                    mapSelectionsView[dayId] = mutableListOf(newSel)
                } else {
                    mapSelectionsView[dayId]!!.add(newSel)
                }
            }
        }


        val listDaysView = mutableListOf<DayView>()
        mapDaysSelections.values.forEach { dayAndSelections ->
            with(DayMapper()) {
                val newDay =
                    dayAndSelections.day.roomToView(mapSelectionsView[dayAndSelections.day.dayId]!!)
                listDaysView.add(newDay)
            }
        }


        val weekSelection = mapSelectionsView[0]?.get(0)
        val weekData =
            with(WeekMapper()) { weekAndDays.week.roomToView(weekSelection!!, listDaysView) }

        return weekData
    }
}