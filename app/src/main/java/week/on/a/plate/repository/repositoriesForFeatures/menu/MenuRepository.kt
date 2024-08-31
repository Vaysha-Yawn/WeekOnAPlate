package week.on.a.plate.repository.repositoriesForFeatures.menu

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import week.on.a.plate.core.data.recipe.RecipeStateView
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.data.week.RecipeInMenuView
import week.on.a.plate.core.data.week.RecipeShortView
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.repository.tables.weekOrg.day.DayAndSelections
import week.on.a.plate.repository.tables.weekOrg.day.DayMapper
import week.on.a.plate.repository.tables.weekOrg.day.DayRepository
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuAndRecipe
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuMapper
import week.on.a.plate.repository.tables.weekOrg.recipeInMenu.RecipeInMenuRepository
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionAndRecipesInMenu
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionMapper
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionRepository
import week.on.a.plate.repository.tables.weekOrg.week.WeekMapper
import week.on.a.plate.repository.tables.weekOrg.week.WeekRepository
import week.on.a.plate.repository.tables.weekOrg.week.WeekRoom
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton


// пояснение здесь мы не добавляем рецепты, а только ссылаемся на их id значит рецепты должны быть уже доступны

@Singleton
class MenuRepository @Inject constructor(
    private val recipeInMenuRepository: RecipeInMenuRepository,
    private val weekRepository: WeekRepository,
    private val dayRepository: DayRepository,
    private val selectionRepository: SelectionRepository
) : IMenuRepository {

    // hold insert
    suspend fun insertWeek(week: WeekView) {
        //
        weekRepository.insert(week)

        //
        insertSelection(0, week.selection)

        //
        week.days.forEach { day ->

            dayRepository.insert(week.id, day)

            //
            day.selections.forEach { sel ->
                insertSelection(day.id, sel)
            }
        }

    }

    // hold insert help
    private suspend fun insertSelection(dayId: Long, sel: SelectionView) {
        selectionRepository.insert(dayId, sel)
        //
        sel.recipes.forEach { recipeInMenu ->
            recipeInMenuRepository.insert(sel.id, recipeInMenu)
        }
    }

    //test
    fun getDay(today: LocalDate): Flow<List<WeekRoom>> {
        return dayRepository.findDay(today).flatMapConcat { day ->
            weekRepository.read()
            //weekRepository.getWeekAndDay(day.weekId)
        }
    }

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

    override suspend fun insertNewWeek(weekView: WeekView) {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentWeek(day: LocalDate): Flow<WeekView> {
        TODO("Not yet implemented")
    }

    //hold get
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getWeekFlow(today: LocalDate): Flow<WeekView> {
        val d = dayRepository.findDay(today).flatMapConcat { day ->
            weekRepository.getWeekAndDay(day.weekId).flatMapConcat { weekAndDays ->

                val weekSelect =
                    selectionRepository.getSelectionAndRecipesInMenu(weekAndDays.week.selectionId)

                val mapDaysSelections = mutableMapOf<Long, Flow<DayAndSelections>>()
                weekAndDays.days.forEach { day ->
                    mapDaysSelections[day.dayId] = dayRepository.getDayAndSelection(day.dayId)
                }

                val mapSelectionAndRecipesInMenu =
                    mutableMapOf<Long, Flow<SelectionAndRecipesInMenu>>()
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
                        selectionAndRecipesInMenu.recipeInMenuRooms.forEach { recipeInMenu ->
                            mapRecipeInMenuAndRecipe[recipeInMenu.recipeInMenuId] =
                                recipeInMenuRepository.getRecipeInMenuAndRecipe(recipeInMenu.recipeId)
                        }
                    }
                }

                val weekRecipeInMenuAndRecipe = mutableListOf<Flow<RecipeInMenuAndRecipe>>()
                weekSelect.map { selectionAndRecipesInMenu ->
                    selectionAndRecipesInMenu.recipeInMenuRooms.forEach { recipeInMenu ->
                        weekRecipeInMenuAndRecipe.add(
                            recipeInMenuRepository.getRecipeInMenuAndRecipe(
                                recipeInMenu.recipeId
                            )
                        )
                    }
                }

                //second

                val mapRecipeInMenuViewView = mutableMapOf<Long, MutableList<RecipeInMenuView>>()
                mapRecipeInMenuAndRecipe.values.forEach { flow ->
                    flow.map { recipeInMenuAndRecipe ->
                        with(RecipeInMenuMapper()) {
                            val newRecipeInMenuView =
                                recipeInMenuAndRecipe.recipeInMenuRoom.roomToView(
                                    recipeInMenuAndRecipe.recipe
                                )
                            val selectionId = recipeInMenuAndRecipe.recipeInMenuRoom.selectionId

                            if (mapRecipeInMenuViewView[selectionId] == null) {
                                mapRecipeInMenuViewView[selectionId] =
                                    mutableListOf(newRecipeInMenuView)
                            } else {
                                mapRecipeInMenuViewView[selectionId]!!.add(newRecipeInMenuView)
                            }
                        }
                    }
                }

                val mapSelectionsView = mutableMapOf<Long, MutableList<SelectionView>>()
                mapSelectionAndRecipesInMenu.values.forEach { flow ->
                    flow.map { sel ->
                        with(SelectionMapper()) {
                            val newSel =
                                sel.selectionRoom.roomToView(mapRecipeInMenuViewView[sel.selectionRoom.selectionId]!!)
                            val dayId = sel.selectionRoom.dayId

                            if (mapSelectionsView[dayId] == null) {
                                mapSelectionsView[dayId] = mutableListOf(newSel)
                            } else {
                                mapSelectionsView[dayId]!!.add(newSel)
                            }
                        }
                    }
                }

                val mapDaysView = mutableListOf<DayView>()
                mapDaysSelections.values.forEach { flow ->
                    flow.map { dayAndSelections ->
                        with(DayMapper()) {
                            val newDay =
                                dayAndSelections.day.roomToView(mapSelectionsView[dayAndSelections.day.dayId]!!)
                            mapDaysView.add(newDay)
                        }
                    }
                }


                val mapWeekRecipeInMenuViewView = mutableListOf<RecipeInMenuView>()
                weekRecipeInMenuAndRecipe.forEach { flow ->
                    flow.map { recipeInMenuAndRecipe ->
                        with(RecipeInMenuMapper()) {
                            val newRecipeInMenuView =
                                recipeInMenuAndRecipe.recipeInMenuRoom.roomToView(
                                    recipeInMenuAndRecipe.recipe
                                )

                            mapWeekRecipeInMenuViewView.add(newRecipeInMenuView)
                        }
                    }
                }

                val mapWeekSelectionsView = weekSelect.map { sel ->
                    with(SelectionMapper()) {
                        sel.selectionRoom.roomToView(mapWeekRecipeInMenuViewView)
                    }
                }

                val weekData = mapWeekSelectionsView.map { weekSelection ->
                    with(WeekMapper()) { weekAndDays.week.roomToView(weekSelection, mapDaysView) }
                }

                weekData
            }
        }

        return d
    }
}