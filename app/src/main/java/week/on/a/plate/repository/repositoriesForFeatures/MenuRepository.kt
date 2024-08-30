package week.on.a.plate.repository.repositoriesForFeatures

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import week.on.a.plate.core.data.week.DayData
import week.on.a.plate.core.data.week.RecipeInMenu
import week.on.a.plate.core.data.week.SelectionInDayData
import week.on.a.plate.core.data.week.WeekData
import week.on.a.plate.repository.tables.weekOrg.day.DayAndSelections
import week.on.a.plate.repository.tables.weekOrg.day.DayDataRepository
import week.on.a.plate.repository.tables.weekOrg.day.DayMapper
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuAndRecipe
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuMapper
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuRepository
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionAndRecipesInMenu
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionInDayMapper
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionInDayRepository
import week.on.a.plate.repository.tables.weekOrg.week.WeekDataRepository
import week.on.a.plate.repository.tables.weekOrg.week.WeekMapper
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepository @Inject constructor(
    private val recipeInMenuRepository: RecipeInMenuRepository,
    private val weekRepository: WeekDataRepository,
    private val dayRepository: DayDataRepository,
    private val selectionRepository: SelectionInDayRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getWeekFlow(today: Date): Flow<WeekData> {
        val d = dayRepository.findDay(today).flatMapConcat { day ->
            weekRepository.getWeekAndDay(day.weekId)
        }.flatMapConcat { weekAndDays ->

            val weekSelect =
                selectionRepository.getSelectionAndRecipesInMenu(weekAndDays.week.selectionId)

            val mapDaysSelections = mutableMapOf<Long, Flow<DayAndSelections>>()
            weekAndDays.days.forEach { day ->
                mapDaysSelections[day.dayId] = dayRepository.getDayAndSelection(day.dayId)
            }

            val mapSelectionAndRecipesInMenu = mutableMapOf<Long, Flow<SelectionAndRecipesInMenu>>()
            mapDaysSelections.values.forEach { flow ->
                flow.map { dayAndSel ->
                    dayAndSel.selections.forEach { sel ->
                        mapSelectionAndRecipesInMenu[sel.selectionId] =
                            selectionRepository.getSelectionAndRecipesInMenu(sel.selectionId)
                    }
                }
            }

            val mapRecipeInMenuAndRecipe = mutableMapOf<Long, Flow<RecipeInMenuAndRecipe>>()
            mapSelectionAndRecipesInMenu.values.forEach { flow ->
                flow.map { selectionAndRecipesInMenu ->
                    selectionAndRecipesInMenu.recipeInMenu.forEach { recipeInMenu ->
                        mapRecipeInMenuAndRecipe[recipeInMenu.recipeInMenuId] =
                            recipeInMenuRepository.getRecipeInMenuAndRecipe(recipeInMenu.recipeId)
                    }
                }
            }

            val weekRecipeInMenuAndRecipe = mutableListOf<Flow<RecipeInMenuAndRecipe>>()
            weekSelect.map { selectionAndRecipesInMenu ->
                selectionAndRecipesInMenu.recipeInMenu.forEach { recipeInMenu ->
                    weekRecipeInMenuAndRecipe.add(
                        recipeInMenuRepository.getRecipeInMenuAndRecipe(
                            recipeInMenu.recipeId
                        )
                    )
                }
            }

            //second

            val mapRecipeInMenuView = mutableMapOf<Long, MutableList<RecipeInMenu>>()
            mapRecipeInMenuAndRecipe.values.forEach { flow ->
                flow.map { recipeInMenuAndRecipe ->
                    with(RecipeInMenuMapper()) {
                        val newRecipeInMenuView =
                            recipeInMenuAndRecipe.recipeInMenu.roomToView(recipeInMenuAndRecipe.recipe)
                        val selectionId = recipeInMenuAndRecipe.recipeInMenu.selectionId

                        if (mapRecipeInMenuView[selectionId] == null) {
                            mapRecipeInMenuView[selectionId] = mutableListOf(newRecipeInMenuView)
                        } else {
                            mapRecipeInMenuView[selectionId]!!.add(newRecipeInMenuView)
                        }
                    }
                }
            }

            val mapSelectionsView = mutableMapOf<Long, MutableList<SelectionInDayData>>()
            mapSelectionAndRecipesInMenu.values.forEach { flow ->
                flow.map { sel ->
                    with(SelectionInDayMapper()) {
                        val newSel =
                            sel.selectionInDay.roomToView(mapRecipeInMenuView[sel.selectionInDay.selectionId]!!)
                        val dayId = sel.selectionInDay.dayId

                        if (mapSelectionsView[dayId] == null) {
                            mapSelectionsView[dayId] = mutableListOf(newSel)
                        } else {
                            mapSelectionsView[dayId]!!.add(newSel)
                        }
                    }
                }
            }

            val mapDaysView = mutableListOf<DayData>()
            mapDaysSelections.values.forEach { flow ->
                flow.map { dayAndSelections ->
                    with(DayMapper()) {
                        val newDay =
                            dayAndSelections.day.roomToView(mapSelectionsView[dayAndSelections.day.dayId]!!)
                        mapDaysView.add(newDay)
                    }
                }
            }


            val mapWeekRecipeInMenuView = mutableListOf<RecipeInMenu>()
            weekRecipeInMenuAndRecipe.forEach { flow ->
                flow.map { recipeInMenuAndRecipe ->
                    with(RecipeInMenuMapper()) {
                        val newRecipeInMenuView =
                            recipeInMenuAndRecipe.recipeInMenu.roomToView(recipeInMenuAndRecipe.recipe)

                        mapWeekRecipeInMenuView.add(newRecipeInMenuView)
                    }
                }
            }

            val mapWeekSelectionsView = weekSelect.map { sel ->
                with(SelectionInDayMapper()) {
                    sel.selectionInDay.roomToView(mapWeekRecipeInMenuView)
                }
            }

            val weekData = mapWeekSelectionsView.map { weekSelection->
                with(WeekMapper()) { weekAndDays.week.roomToView(weekSelection, mapDaysView) }
            }

            weekData
        }
        return d
    }


    suspend fun getWeek(today: Date): WeekData {
        // цель с помощью наименьшего кол-ва вызовов собрать картину week по кусочкам и отправить
        // желательно при этом не нарушать Flow, чтобы все изменения в объектах отслеживались

        val mapDayRoom = mutableMapOf<Long, week.on.a.plate.repository.tables.weekOrg.day.DayData>()
        val mapSelectionRoom =
            mutableMapOf<Long, week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionInDay>()
        val mapRecipeInMenuRoom =
            mutableMapOf<Long, week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenu>()
        val mapRecipeRoom =
            mutableMapOf<Long, week.on.a.plate.repository.tables.recipe.recipe.Recipe>()


        // 1. get today
        val todayDay = dayRepository.findDay(today).first()
        mapDayRoom[todayDay.dayId] = todayDay

        // 2. get week and days in current week
        val currentWeekAndDay = weekRepository.getWeekAndDay(todayDay.weekId).first()
        val weekRoom = currentWeekAndDay.week
        currentWeekAndDay.days.forEach { day ->
            mapDayRoom[day.dayId] = day
        }

        //3. get selectionAndRecipesInMenuWeek for нераспред недели
        val selectionAndRecipesInMenuWeek =
            selectionRepository.getSelectionAndRecipesInMenu(currentWeekAndDay.week.selectionId)
                .first()
        mapSelectionRoom[selectionAndRecipesInMenuWeek.selectionInDay.selectionId] =
            selectionAndRecipesInMenuWeek.selectionInDay
        selectionAndRecipesInMenuWeek.recipeInMenu.forEach { recipeInMenu ->
            mapRecipeInMenuRoom[recipeInMenu.recipeInMenuId] = recipeInMenu
        }

        // 4. get selections for days
        mapDayRoom.keys.forEach { dayId ->
            val sels = dayRepository.getDayAndSelection(dayId).first()
            sels.selections.forEach { sel ->
                mapSelectionRoom[sel.selectionId] = sel
            }
        }

        // 5. get recipes for selections as in 3
        mapSelectionRoom.keys.forEach {
            val seAndRecInM = selectionRepository.getSelectionAndRecipesInMenu(it).first()
            seAndRecInM.recipeInMenu.forEach { recipeInMenu ->
                mapRecipeInMenuRoom[recipeInMenu.recipeInMenuId] = recipeInMenu
            }
        }

        // 6. recipe
        mapRecipeInMenuRoom.keys.forEach {
            val recipeInMenuAnRecipe = recipeInMenuRepository.getRecipeInMenuAndRecipe(it).first()
            mapRecipeRoom[recipeInMenuAnRecipe.recipe.recipeId] = recipeInMenuAnRecipe.recipe
        }

        ///// 2 этап собираем

        val days = mutableListOf<DayData>()
        val mapSelection = mutableMapOf<Long, SelectionInDayData>()
        val mapRecipeInMenu = mutableMapOf<Long, RecipeInMenu>()

        //1 RecipeInMenu
        mapRecipeInMenuRoom.values.forEach {
            val recipe = mapRecipeRoom[it.recipeId]!!
            with(RecipeInMenuMapper()) {
                val recipeInMenuView = it.roomToView(recipe)
                mapRecipeInMenu[recipeInMenuView.id] = recipeInMenuView
            }
        }

        //2 RecipeInMenu - группировка по list с одинаковым selectionId
        val mapRecipeInMenuFoSel = mutableMapOf<Long, MutableList<RecipeInMenu>>()
        mapRecipeInMenuRoom.values.forEach {
            if (mapRecipeInMenuFoSel[it.selectionId] == null) {
                mapRecipeInMenuFoSel[it.selectionId] =
                    mutableListOf(mapRecipeInMenu[it.recipeInMenuId]!!)
            } else {
                mapRecipeInMenuFoSel[it.selectionId]!!.add(mapRecipeInMenu[it.recipeInMenuId]!!)
            }
        }

        //3 Selection
        mapRecipeInMenuRoom.values.forEach {
            val sel = mapSelectionRoom[it.selectionId]!!
            with(SelectionInDayMapper()) {
                val newSel = sel.roomToView(mapRecipeInMenuFoSel[it.selectionId]!!)
                mapSelection[it.selectionId] = newSel
            }
        }

        // 4
        val weekSelection = mapSelection[weekRoom.selectionId]
        mapSelection.remove(weekRoom.selectionId)


        //5 selections - группировка по list с одинаковым dayId
        val mapSelForDay = mutableMapOf<Long, MutableList<SelectionInDayData>>()
        mapSelectionRoom.values.forEach {
            if (mapSelForDay[it.dayId] == null) {
                mapSelForDay[it.dayId] = mutableListOf(mapSelection[it.selectionId]!!)
            } else {
                mapSelForDay[it.dayId]!!.add(mapSelection[it.selectionId]!!)
            }
        }

        // 5 day
        mapDayRoom.values.forEach { day ->
            with(DayMapper()) {
                val newDay = day.roomToView(mapSelForDay[day.dayId]!!)
                days.add(newDay)
            }
        }

        //6
        val week = with(WeekMapper()) { weekRoom.roomToView(weekSelection!!, days) }

        return week
    }
}



