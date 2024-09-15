package week.on.a.plate.repository.repositoriesForFeatures.menu

import week.on.a.plate.core.data.example.emptyDay
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.core.data.week.DayInWeekData
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.repository.tables.recipe.ingredient.IngredientMapper
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeDAO
import week.on.a.plate.repository.tables.recipe.ingredientInRecipe.IngredientInRecipeMapper
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTagDAO
import week.on.a.plate.repository.tables.recipe.recipeTag.RecipeTagMapper
import week.on.a.plate.repository.tables.weekOrg.day.DayDAO
import week.on.a.plate.repository.tables.weekOrg.day.DayMapper
import week.on.a.plate.repository.tables.weekOrg.day.DayRoom
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftDAO
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftMapper
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftRepository
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientDAO
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientRepository
import week.on.a.plate.repository.tables.weekOrg.position.positionNote.PositionNoteRepository
import week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu.PositionRecipeRepository
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionDAO
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionMapper
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionRoom
import week.on.a.plate.repository.tables.weekOrg.week.WeekDAO
import week.on.a.plate.repository.tables.weekOrg.week.WeekMapper
import week.on.a.plate.repository.tables.weekOrg.week.WeekRoom
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton


// пояснение здесь мы не добавляем рецепты, а только ссылаемся на их id значит рецепты должны быть уже доступны

@Singleton
class MenuRepository @Inject constructor(
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

    val fetWeekFun: GetWeekFun,
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
                    //
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
}