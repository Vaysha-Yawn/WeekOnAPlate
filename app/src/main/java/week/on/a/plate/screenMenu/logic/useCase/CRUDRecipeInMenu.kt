package week.on.a.plate.screenMenu.logic.useCase

import week.on.a.plate.data.dataView.example.emptyDayWithoutSel
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.week.DayInWeekData
import week.on.a.plate.data.dataView.week.DayView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.screenMenu.event.ActionWeekMenuDB
import week.on.a.plate.data.repository.tables.menu.week.WeekRepository
import week.on.a.plate.data.repository.tables.recipe.ingredient.IngredientDAO
import week.on.a.plate.data.repository.tables.recipe.ingredient.IngredientMapper
import week.on.a.plate.data.repository.tables.recipe.recipeTag.RecipeTagDAO
import week.on.a.plate.data.repository.tables.recipe.recipeTag.RecipeTagMapper
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftDAO
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftMapper
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftRepository
import week.on.a.plate.data.repository.tables.menu.position.positionIngredient.PositionIngredientRepository
import week.on.a.plate.data.repository.tables.menu.position.note.PositionNoteRepository
import week.on.a.plate.data.repository.tables.menu.position.positionRecipe.PositionRecipeRepository
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

class CRUDRecipeInMenu @Inject constructor(
    val menuR: WeekRepository,
    private val noteRepository: PositionNoteRepository,
    private val positionIngredientRepository: PositionIngredientRepository,
    private val recipeRepository: PositionRecipeRepository,
    private val tagDAO: RecipeTagDAO,
    private val draftRepository: PositionDraftRepository,
    private val positionDraftDAO: PositionDraftDAO,
    private val ingredientDAO: IngredientDAO,
) {
    suspend fun onEvent(event: ActionWeekMenuDB, selected: List<Position>) {
        when (event) {
            is ActionWeekMenuDB.AddEmptyDay -> {
                menuR.addEmptyDay(event.data)
            }

            is ActionWeekMenuDB.AddIngredientPositionDB -> {
                val ingredient = event.updatedPosition
                positionIngredientRepository.insert(ingredient, event.selectionId)
            }

            is ActionWeekMenuDB.AddNoteDB -> {
                noteRepository.insert(
                    Position.PositionNoteView(0, event.text, event.selectionId),
                    event.selectionId
                )
            }

            is ActionWeekMenuDB.AddRecipePositionInMenuDB -> {
                recipeRepository.insert(event.recipe, event.selId)
            }

            is ActionWeekMenuDB.ChangePortionsCountDB -> {
                recipeRepository.update(
                    event.recipe.id,
                    event.recipe.recipe,
                    event.count,
                    event.recipe.selectionId
                )
            }

            is ActionWeekMenuDB.Delete -> {
                when (event.position) {
                    is Position.PositionDraftView -> draftRepository.delete(event.position.idPos)
                    is Position.PositionIngredientView -> positionIngredientRepository.delete(event.position.idPos)
                    is Position.PositionNoteView -> noteRepository.delete(event.position.idPos)
                    is Position.PositionRecipeView -> recipeRepository.delete(event.position.idPos)
                }
            }

            ActionWeekMenuDB.DeleteSelected -> {
                selected.forEach {
                    onEvent(ActionWeekMenuDB.Delete(it), listOf())
                }
            }

            is ActionWeekMenuDB.DoublePositionInMenuDB -> {
                val selId: Long = event.selId
                when (event.position) {
                    is Position.PositionDraftView -> draftRepository.insert(event.position, selId)
                    is Position.PositionIngredientView -> positionIngredientRepository.insert(
                        event.position,
                        selId
                    )

                    is Position.PositionNoteView -> noteRepository.insert(event.position, selId)
                    is Position.PositionRecipeView -> recipeRepository.insert(event.position, selId)
                }
            }

            is ActionWeekMenuDB.EditIngredientPositionDB -> {
                val data = event.updatedPosition
                positionIngredientRepository.update(
                    data.id,
                    data.ingredient.id,
                    data.ingredient.ingredientView.ingredientId,
                    data.selectionId,
                    data.ingredient.description,
                    data.ingredient.count,
                )
            }

            is ActionWeekMenuDB.EditNoteDB -> {
                val note = event.data
                noteRepository.update(
                    note.idPos,
                    note.note, note.selectionId
                )
            }

            is ActionWeekMenuDB.MovePositionInMenuDB -> {
                val selId: Long = event.selId
                when (event.position) {
                    is Position.PositionDraftView -> {
                        onEvent(ActionWeekMenuDB.Delete(event.position), listOf())
                        draftRepository.insert(event.position, selId)
                    }

                    is Position.PositionIngredientView -> {
                        onEvent(ActionWeekMenuDB.Delete(event.position), listOf())
                        positionIngredientRepository.insert(event.position, selId)
                    }

                    is Position.PositionNoteView -> {
                        onEvent(ActionWeekMenuDB.Delete(event.position), listOf())
                        noteRepository.insert(event.position, selId)
                    }

                    is Position.PositionRecipeView -> {
                        onEvent(ActionWeekMenuDB.Delete(event.position), listOf())
                        recipeRepository.insert(event.position, selId)
                    }
                }
            }

            is ActionWeekMenuDB.AddDraft -> {
                val draftView = event.draft
                val newId = draftRepository.insert(draftView, event.draft.selectionId)

                //todo move to recipe module
                draftView.tags.forEach { recipeTagView ->
                    val tagId = findOrCreateTag(recipeTagView)
                    val tagRefs = with(PositionDraftMapper()) {
                        genTagCrossRef(newId, tagId)
                    }
                    positionDraftDAO.insertDraftAndTagCrossRef(tagRefs)
                }

                //todo move to recipe module
                draftView.ingredients.forEach { ingredientView ->
                    val ingredientId = findOrCreateIngredient(ingredientView)
                    val ingredientRefs = with(PositionDraftMapper()) {
                        genIngredientCrossRef(newId, ingredientId)
                    }
                    positionDraftDAO.insertDraftAndIngredientCrossRef(ingredientRefs)
                }
            }

            is ActionWeekMenuDB.EditDraft -> {
                val oldDraft = event.oldDraft
                val tags = event.filters.first
                val ingredients = event.filters.second

                val listTagsRemove = oldDraft.tags.toMutableList().apply {
                    this.removeAll(tags)
                }
                val listIngredientRemove = oldDraft.ingredients.toMutableList().apply {
                    this.removeAll(ingredients)
                }
                val listTagsAdd = tags.toMutableList().apply {
                    this.removeAll(oldDraft.tags)
                }
                val listIngredientAdd = ingredients.toMutableList().apply {
                    this.removeAll(oldDraft.ingredients)
                }
                listTagsRemove.forEach {
                    positionDraftDAO.deleteByIdTag(it.id)
                }
                listIngredientRemove.forEach {
                    positionDraftDAO.deleteByIdIngredient(it.ingredientId)
                }
                listTagsAdd.forEach {
                    val tagRefs = with(PositionDraftMapper()) {
                        genTagCrossRef(oldDraft.id, it.id)
                    }
                    positionDraftDAO.insertDraftAndTagCrossRef(tagRefs)
                }
                listIngredientAdd.forEach {
                    val ingredientRefs = with(PositionDraftMapper()) {
                        genIngredientCrossRef(oldDraft.id, it.ingredientId)
                    }
                    positionDraftDAO.insertDraftAndIngredientCrossRef(ingredientRefs)
                }
            }
        }
    }

    private suspend fun findOrCreateIngredient(ingredientView: IngredientView): Long {
        val ingredientLast = ingredientDAO.findByName(ingredientView.name)
        val ingredientId = if (ingredientLast != null) {
            val tagView = with(IngredientMapper()) {
                ingredientLast.roomToView()
            }
            tagView.ingredientId
        } else {
            val ingredientRoom = with(IngredientMapper()) {
                ingredientView.viewToRoom(0)
            }
            ingredientDAO.insert(ingredientRoom)
        }
        return ingredientId
    }

    private suspend fun findOrCreateTag(recipeTagView: RecipeTagView):Long{
        val tagLast = tagDAO.findByName(recipeTagView.tagName)

        val tagId = if (tagLast != null) {
            val tagView = with(RecipeTagMapper()) {
                tagLast.roomToView()
            }
            tagView.id
        } else {
            val recipeTagRoom = with(RecipeTagMapper()) {
                recipeTagView.viewToRoom(0)
            }
            tagDAO.insert(recipeTagRoom)
        }
        return tagId
    }

    private fun getRelatedDate(dateStart: LocalDate, d: DayOfWeek): LocalDate {
        if (dateStart.dayOfWeek == d) {
            return dateStart
        } else {
            val offset = dateStart.dayOfWeek.value - d.value
            return dateStart.minusDays(offset.toLong())
        }
    }

    suspend fun insertEmptyWeek(activeDay: LocalDate) {
        menuR.insertNewWeek(getEmptyWeek(activeDay))
    }

    fun getWeekParted(week: WeekView): WeekView {
        val newList = getDayParted(week.days)
        newList.sortBy { it -> it.date }
        week.days = newList
        return week
    }

    private fun getDayParted(listHave: MutableList<DayView>): MutableList<DayView> {
        val list = mutableListOf<DayOfWeek>()
        listHave.forEach {
            list.add(it.dayInWeek.dayOfWeekMapper())
        }
        val listAll = mutableListOf<DayView>()
        val started = listHave[0].date
        DayOfWeek.entries.forEach { dayOfWeek ->
            if (list.contains(dayOfWeek)) {
                listAll.add(listHave.find { day -> day.dayInWeek.dayOfWeekMapper() == dayOfWeek }!!)
            } else {
                val rel = getRelatedDate(started, dayOfWeek)
                listAll.add(getDayView(rel))
            }
        }
        return listAll
    }

    private fun DayInWeekData.dayOfWeekMapper(): DayOfWeek {
        return when (this) {
            DayInWeekData.Mon -> DayOfWeek.MONDAY
            DayInWeekData.Tues -> DayOfWeek.TUESDAY
            DayInWeekData.Wed -> DayOfWeek.WEDNESDAY
            DayInWeekData.Thurs -> DayOfWeek.THURSDAY
            DayInWeekData.Fri -> DayOfWeek.FRIDAY
            DayInWeekData.Sat -> DayOfWeek.SATURDAY
            DayInWeekData.Sun -> DayOfWeek.SUNDAY
        }
    }


    private fun getDayView(d: LocalDate): DayView {
        return when (d.dayOfWeek) {
            DayOfWeek.MONDAY -> {
                DayView(1, d, DayInWeekData.Mon, emptyDayWithoutSel)
            }

            DayOfWeek.TUESDAY -> {
                DayView(2, d, DayInWeekData.Tues, emptyDayWithoutSel)
            }

            DayOfWeek.WEDNESDAY -> {
                DayView(3, d, DayInWeekData.Wed, emptyDayWithoutSel)
            }

            DayOfWeek.THURSDAY -> {
                DayView(4, d, DayInWeekData.Thurs, emptyDayWithoutSel)
            }

            DayOfWeek.FRIDAY -> {
                DayView(5, d, DayInWeekData.Fri, emptyDayWithoutSel)
            }

            DayOfWeek.SATURDAY -> {
                DayView(6, d, DayInWeekData.Sat, emptyDayWithoutSel)
            }

            DayOfWeek.SUNDAY -> {
                DayView(7, d, DayInWeekData.Sun, emptyDayWithoutSel)
            }

            null -> {
                DayView(7, d, DayInWeekData.Sun, emptyDayWithoutSel)
            }
        }
    }

    fun getEmptyWeek(activeDay: LocalDate): WeekView {
        val weekDate = mutableListOf<LocalDate>()
        DayOfWeek.entries.forEach {
            val dayInWeek = getRelatedDate(activeDay, it)
            weekDate.add(dayInWeek)
        }
        val week = WeekView(
            0,
            SelectionView(0, "", mutableListOf()),
            mutableListOf(
                getDayView(weekDate[0]),
                getDayView(weekDate[1]),
                getDayView(weekDate[2]),
                getDayView(weekDate[3]),
                getDayView(weekDate[4]),
                getDayView(weekDate[5]),
                getDayView(weekDate[6]),
            )
        )
        return week
    }
}