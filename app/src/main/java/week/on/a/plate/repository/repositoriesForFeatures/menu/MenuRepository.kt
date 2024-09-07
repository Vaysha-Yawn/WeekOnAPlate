package week.on.a.plate.repository.repositoriesForFeatures.menu

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.RecipeShortView
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.repository.tables.recipe.ingredient.IngredientMapper
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeDAO
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeMapper
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTagMapper
import week.on.a.plate.repository.tables.weekOrg.day.DayDAO
import week.on.a.plate.repository.tables.weekOrg.day.DayMapper
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftDAO
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftMapper
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftRoom
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientAndIngredientInRecipeView
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientDAO
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientMapper
import week.on.a.plate.repository.tables.weekOrg.position.positionNote.PositionNoteDAO
import week.on.a.plate.repository.tables.weekOrg.position.positionNote.PositionNoteMapper
import week.on.a.plate.repository.tables.weekOrg.position.positionNote.PositionNoteRoom
import week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu.PositionRecipeRoom
import week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu.RecipeInMenuDAO
import week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu.RecipeInMenuMapper
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
    private val positionDraftDAO: PositionDraftDAO,
    private val positionIngredientDAO: PositionIngredientDAO,
    private val positionNoteDAO: PositionNoteDAO,
    private val weekDAO: WeekDAO,
    private val dayDAO: DayDAO,
    private val selectionDAO: SelectionDAO,
    private val ingredientInRecipeDAO: IngredientInRecipeDAO,
) : IMenuRepository {

    override suspend fun addRecipeInMenu() {
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

                selection.positions.forEach { position ->
                    when (position) {
                        is Position.PositionDraftView -> {
                            val positionRoom = with(PositionDraftMapper()) {
                                position.viewToRoom(selectionId)
                            }
                            val newId = positionDraftDAO.insert(positionRoom)

                            val tagRefs = with(PositionDraftMapper()) {
                                position.genTagCrossRefs(newId)
                            }
                            positionDraftDAO.insertAll(tagRefs)

                            val ingredientRefs = with(PositionDraftMapper()) {
                                position.genIngredientCrossRefs(newId)
                            }
                            positionDraftDAO.insertAll(ingredientRefs)
                        }

                        is Position.PositionIngredientView -> {
                            val positionRoom = with(PositionIngredientMapper()) {
                                position.viewToRoom(selectionId)
                            }
                            positionIngredientDAO.insert(positionRoom)
                            val ingredientRoom = with(IngredientInRecipeMapper()) {
                                position.ingredient.viewToRoom(0)//todo мб сделать кросс реф
                            }
                            positionIngredientDAO.insert(ingredientRoom)
                        }

                        is Position.PositionNoteView -> {
                            val positionRoom = with(PositionNoteMapper()) {
                                position.viewToRoom(selectionId)
                            }
                            positionNoteDAO.insert(positionRoom)
                        }

                        is Position.PositionRecipeView -> {
                            val recipeInMenuRoom = with(RecipeInMenuMapper()) {
                                position.viewToRoom(selectionId)
                            }
                            recipeInMenuDAO.insert(recipeInMenuRoom)
                        }
                    }
                }
            }
        }
    }

    override suspend fun getCurrentWeek(day: LocalDate): Flow<WeekView> {
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

            var flowList = flow { emit(mutableListOf<SelectionView>()) }
            listSelection.forEach { flow ->
                flowList = flowList.combine(flow) { f1, f2 ->
                    f1.add(f2)
                    f1
                }
            }

            val newDayFlow = flowList.transform<MutableList<SelectionView>, DayView> { listSel ->
                val newDay = with(DayMapper()) {
                    dayAndSelections.day.roomToView(listSel)
                }
                emit(newDay)
            }

            listDaysView.add(newDayFlow)
        }

        var flowListDay = flow { emit(mutableListOf<DayView>()) }
        listDaysView.forEach { flow ->
            flowListDay = flowListDay.combine(flow) { f1, f2 ->
                f1.add(f2)
                f1
            }
        }

        val weekSelectAndRecipesInMenu =
            selectionDAO.getSelectionAndRecipesInMenu(weekAndDays.week.selectionId)
        val weekSel = mapSelection(weekSelectAndRecipesInMenu)

        val weekFlow = weekSel.combine(flowListDay) { sel, listDay ->
            with(WeekMapper()) { weekAndDays.week.roomToView(sel, listDay) }
        }

        return weekFlow
    }


    private suspend fun mapSelection(selectionAndRecipesInMenu: SelectionAndRecipesInMenu): Flow<SelectionView> {
        val flowListPositionRecipe =
            recipeInMenuDAO.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)
                .transform<List<PositionRecipeRoom>, List<Position>> {
                    val list = mutableListOf<Position>()

                    it.forEach { recipeInMenu ->
                        with(RecipeInMenuMapper()) {
                            val newRecipeInMenuView =
                                recipeInMenu.roomToView(
                                    recipeInMenu.recipeId, recipeInMenu.recipeName
                                )
                            list.add(newRecipeInMenuView)
                        }
                    }
                    emit(list)
                }

        val flowListPositionIngredient =
            positionIngredientDAO.getAllInSelCrossRef(selectionAndRecipesInMenu.selectionRoom.selectionId)
                .transform<List<PositionIngredientAndIngredientInRecipeView>, List<Position>> {
                    val list = mutableListOf<Position>()
                    it.forEach { AandB ->
                        val position = AandB.positionIngredientRoom
                        val ingredientInMenu = AandB.ingredientInRecipeRoom

                        val ingredientRoom =
                            ingredientInRecipeDAO.getCurrent(ingredientInMenu.ingredientId)
                        val ingredientView =
                            with(IngredientMapper()) { ingredientRoom.roomToView() }

                        val ingredientInMenuView = with(IngredientInRecipeMapper()) {
                            ingredientInMenu.roomToView(ingredientView)
                        }
                        with(PositionIngredientMapper()) {
                            val positionIngredientView =
                                position.roomToView(ingredientInRecipe = ingredientInMenuView)
                            list.add(positionIngredientView)
                        }
                    }
                    emit(list)
                }

        val flowListPositionNote =
            positionNoteDAO.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)
                .transform<List<PositionNoteRoom>, List<Position>> {
                    val list = mutableListOf<Position>()
                    it.forEach { noteRoom ->
                        with(PositionNoteMapper()) {
                            val noteView =
                                noteRoom.roomToView()
                            list.add(noteView)
                        }
                    }
                    emit(list)
                }


        val flowListPositionDraft =
            positionDraftDAO.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)
                .transform<List<PositionDraftRoom>, List<Position>> {
                    val list = mutableListOf<Position>()
                    it.forEach { draftRoom ->
                        val draftAndTags = positionDraftDAO.getDraftAndTagByDraftId(draftRoom.id)
                        val listTags = mutableListOf<RecipeTagView>()
                        draftAndTags.tags.forEach {
                            with(RecipeTagMapper()) {
                                val tagView = it.roomToView()
                                listTags.add(tagView)
                            }
                        }

                        val draftAndIngredient =
                            positionDraftDAO.getDraftAndIngredientByDraftId(draftRoom.id)
                        val listIngredients = mutableListOf<IngredientView>()
                        draftAndIngredient.ingredients.forEach {
                            with(IngredientMapper()) {
                                val ingredientView = it.roomToView()
                                listIngredients.add(ingredientView)
                            }
                        }

                        with(PositionDraftMapper()) {
                            val draftView =
                                draftRoom.roomToView(
                                    tags = listTags,
                                    ingredients = listIngredients
                                )
                            list.add(draftView)
                        }

                    }
                    emit(list)
                }


        var flowListPosition = flowListPositionRecipe.combine(flowListPositionIngredient){list1,list2->
            val combinedList = list1.toMutableList()
            combinedList.addAll(list2)
            combinedList
        }
        flowListPosition = flowListPosition.combine(flowListPositionNote){list1,list2->
            val combinedList = list1.toMutableList()
            combinedList.addAll(list2)
            combinedList
        }

        flowListPosition = flowListPosition.combine(flowListPositionDraft){list1,list2->
            val combinedList = list1.toMutableList()
            combinedList.addAll(list2)
            combinedList
        }

        val sel = flowListPosition.transform<MutableList<Position>, SelectionView>{ listPos->
            val newSel = with(SelectionMapper()) {
                selectionAndRecipesInMenu.selectionRoom.roomToView(listPos)
            }
            emit(newSel)
        }
        
        return sel
    }
}