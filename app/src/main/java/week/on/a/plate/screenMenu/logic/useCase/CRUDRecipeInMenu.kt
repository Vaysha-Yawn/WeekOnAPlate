package week.on.a.plate.screenMenu.logic.useCase

import week.on.a.plate.data.dataView.example.emptyDayWithoutSel
import week.on.a.plate.data.dataView.week.DayInWeekData
import week.on.a.plate.data.dataView.week.DayView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftRepository
import week.on.a.plate.data.repository.tables.menu.position.note.PositionNoteRepository
import week.on.a.plate.data.repository.tables.menu.position.positionIngredient.PositionIngredientRepository
import week.on.a.plate.data.repository.tables.menu.position.positionRecipe.PositionRecipeRepository
import week.on.a.plate.data.repository.tables.menu.week.WeekRepository
import week.on.a.plate.screenMenu.event.ActionWeekMenuDB
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

class CRUDRecipeInMenu @Inject constructor(
    val menuR: WeekRepository,
    private val noteRepository: PositionNoteRepository,
    private val positionIngredientRepository: PositionIngredientRepository,
    private val recipeRepository: PositionRecipeRepository,
    private val draftRepository: PositionDraftRepository,
) {
    suspend fun onEvent(event: ActionWeekMenuDB) {
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
                    data.ingredient.count.toDouble(),
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
                        onEvent(ActionWeekMenuDB.Delete(event.position))
                        draftRepository.insert(event.position, selId)
                    }

                    is Position.PositionIngredientView -> {
                        onEvent(ActionWeekMenuDB.Delete(event.position))
                        positionIngredientRepository.insert(event.position, selId)
                    }

                    is Position.PositionNoteView -> {
                        onEvent(ActionWeekMenuDB.Delete(event.position))
                        noteRepository.insert(event.position, selId)
                    }

                    is Position.PositionRecipeView -> {
                        onEvent(ActionWeekMenuDB.Delete(event.position))
                        recipeRepository.insert(event.position, selId)
                    }
                }
            }

            is ActionWeekMenuDB.AddDraft -> {
                draftRepository.insert(event.draft, event.draft.selectionId)
            }

            is ActionWeekMenuDB.EditDraft -> {
                draftRepository.update(event.oldDraft, event.filters)
            }
        }
    }

    private fun getRelatedDate(dateStart: LocalDate, d: DayOfWeek): LocalDate {
        return dateStart.with(d)
    }

    fun getWeekParted(week: WeekView): WeekView {
        val newList = getDayParted(week.days)
        newList.sortBy { it -> it.date }
        week.days = newList
        return week
    }

    private fun getDayParted(listHave: List<DayView>): MutableList<DayView> {
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
        val dayInWeek = DayInWeekData.entries[d.dayOfWeek.ordinal]
        return DayView(0, d, dayInWeek, emptyDayWithoutSel)
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