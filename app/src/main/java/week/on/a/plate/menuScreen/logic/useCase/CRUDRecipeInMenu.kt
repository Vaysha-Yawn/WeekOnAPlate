package week.on.a.plate.menuScreen.logic.useCase

import week.on.a.plate.core.data.example.emptyDay
import week.on.a.plate.core.data.recipe.IngredientInRecipeView
import week.on.a.plate.core.data.week.DayInWeekData
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.menuScreen.logic.eventData.ActionDBData
import week.on.a.plate.repository.repositoriesForFeatures.menu.MenuRepository
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftRepository
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientRepository
import week.on.a.plate.repository.tables.weekOrg.position.positionNote.PositionNoteRepository
import week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu.PositionRecipeRepository
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

class CRUDRecipeInMenu @Inject constructor(
    val menuR: MenuRepository,
    private val noteRepository: PositionNoteRepository,
    private val positionIngredientRepository: PositionIngredientRepository,
    private val recipeRepository: PositionRecipeRepository,
    private val draftRepository: PositionDraftRepository,
) {
    suspend fun onEvent(event: ActionDBData, selected: List<Position>) {
        when (event) {
            is ActionDBData.AddEmptyDay -> {
                menuR.addEmptyDay(event.data)
            }

            is ActionDBData.AddIngredientDB -> {
                val ingredient = Position.PositionIngredientView(
                    0, IngredientInRecipeView(
                        0,
                        event.data.ingredientState.value!!,
                        event.data.text.value,
                        event.data.count.doubleValue
                    ), event.data.selectionId
                )
                positionIngredientRepository.insert(ingredient, event.data.selectionId)
            }

            is ActionDBData.AddNoteDB -> {
                noteRepository.insert(
                    Position.PositionNoteView(0, event.data.text.value, event.data.selectionId),
                    event.data.selectionId
                )
            }

            is ActionDBData.AddRecipePositionInMenuDB -> {
                recipeRepository.insert(event.recipe, event.selId)
            }

            is ActionDBData.ChangePortionsCountDB -> {
                recipeRepository.update(
                    event.data.recipe.id,
                    event.data.recipe.recipe,
                    event.data.portionsCount.intValue,
                    event.data.recipe.selectionId
                )
            }

            is ActionDBData.Delete -> {
                when (event.position) {
                    is Position.PositionDraftView -> draftRepository.delete(event.position.idPos)
                    is Position.PositionIngredientView -> positionIngredientRepository.delete(event.position.idPos)
                    is Position.PositionNoteView -> noteRepository.delete(event.position.idPos)
                    is Position.PositionRecipeView -> recipeRepository.delete(event.position.idPos)
                }
            }

            ActionDBData.DeleteSelected -> {
                selected.forEach {
                    onEvent(ActionDBData.Delete(it), listOf())
                }
            }

            is ActionDBData.DoublePositionInMenuDB -> {
                val selId: Long = menuR.getSelIdOrCreate(event.dateToLocalDate, event.selection)
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

            is ActionDBData.EditIngredientDB -> {
                val data = event.data
                positionIngredientRepository.update(
                    data.ingredient!!.idPos,
                    data.ingredient.ingredient.id,
                    data.ingredientState.value!!.ingredientId,
                    data.ingredient.selectionId,
                    event.data.text.value,
                    event.data.count.doubleValue
                )
            }

            is ActionDBData.EditNoteDB -> {
                val note = event.data.note
                noteRepository.update(
                    note.idPos,
                    event.data.text.value, note.selectionId
                )
            }

            is ActionDBData.MovePositionInMenuDB -> {
                val selId: Long = menuR.getSelIdOrCreate(event.dateToLocalDate, event.selection)
                when (event.position) {
                    is Position.PositionDraftView -> {
                        onEvent(ActionDBData.Delete(event.position), listOf())
                        draftRepository.insert(event.position, selId)
                    }

                    is Position.PositionIngredientView -> {
                        onEvent(ActionDBData.Delete(event.position), listOf())
                        positionIngredientRepository.insert(event.position, selId)
                    }

                    is Position.PositionNoteView -> {
                        onEvent(ActionDBData.Delete(event.position), listOf())
                        noteRepository.insert(event.position, selId)
                    }

                    is Position.PositionRecipeView -> {
                        onEvent(ActionDBData.Delete(event.position), listOf())
                        recipeRepository.insert(event.position, selId)
                    }
                }
            }
        }
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
        newList.sortBy { it-> it.date }
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
        DayOfWeek.entries.forEach { dayOfWeek->
            if (list.contains(dayOfWeek)){
                listAll.add(listHave.find { day-> day.dayInWeek.dayOfWeekMapper() == dayOfWeek }!!)
            }else{
                val rel = getRelatedDate(started, dayOfWeek)
                listAll.add(getDayView(rel))
            }
        }
        return listAll
    }

    private fun DayInWeekData.dayOfWeekMapper():DayOfWeek{
        return when(this){
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
                DayView(1, d, DayInWeekData.Mon, emptyDay)
            }

            DayOfWeek.TUESDAY -> {
                DayView(2, d, DayInWeekData.Tues, emptyDay)
            }

            DayOfWeek.WEDNESDAY -> {
                DayView(3, d, DayInWeekData.Wed, emptyDay)
            }

            DayOfWeek.THURSDAY -> {
                DayView(4, d, DayInWeekData.Thurs, emptyDay)
            }

            DayOfWeek.FRIDAY -> {
                DayView(5, d, DayInWeekData.Fri, emptyDay)
            }

            DayOfWeek.SATURDAY -> {
                DayView(6, d, DayInWeekData.Sat, emptyDay)
            }

            DayOfWeek.SUNDAY -> {
                DayView(7, d, DayInWeekData.Sun, emptyDay)
            }

            null -> {
                DayView(7, d, DayInWeekData.Sun, emptyDay)
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