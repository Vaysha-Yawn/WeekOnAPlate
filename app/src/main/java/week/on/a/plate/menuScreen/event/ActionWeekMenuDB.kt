package week.on.a.plate.menuScreen.event

import week.on.a.plate.core.data.week.Position
import java.time.LocalDate

sealed class ActionWeekMenuDB {
    class EditNoteDB(val data: Position.PositionNoteView) : ActionWeekMenuDB()
    class AddNoteDB(val text: String, val selectionId: Long) : ActionWeekMenuDB()

    class AddRecipePositionInMenuDB(
        val selId: Long,
        val recipe: Position.PositionRecipeView
    ) : ActionWeekMenuDB()

    class EditIngredientPositionDB(val updatedPosition: Position.PositionIngredientView) :
        ActionWeekMenuDB()

    class ChangePortionsCountDB(val recipe: Position.PositionRecipeView, val count: Int) :
        ActionWeekMenuDB()

    class AddIngredientPositionDB(
        val updatedPosition: Position.PositionIngredientView,
        val selectionId: Long
    ) : ActionWeekMenuDB()

    class MovePositionInMenuDB(
        val selId: Long,
        val position: Position
    ) : ActionWeekMenuDB()

    class DoublePositionInMenuDB(
        val selId: Long,
        val position: Position
    ) : ActionWeekMenuDB()

    data object DeleteSelected : ActionWeekMenuDB()
    class Delete(val position: Position) : ActionWeekMenuDB()
    class AddEmptyDay(val data: LocalDate) : ActionWeekMenuDB()
}