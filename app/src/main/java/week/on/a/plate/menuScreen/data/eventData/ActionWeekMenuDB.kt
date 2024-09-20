package week.on.a.plate.menuScreen.data.eventData

import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.dialogs.data.DialogData
import java.time.LocalDate

sealed class ActionWeekMenuDB{
    class EditNoteDB(val data: DialogData.EditNote) : ActionWeekMenuDB()
    class AddNoteDB(val text:String, val selectionId:Long) : ActionWeekMenuDB()

    class AddRecipePositionInMenuDB(
        val selId:Long,
        val recipe: Position.PositionRecipeView
    ) : ActionWeekMenuDB()

    class EditIngredientDB(val data: DialogData.EditIngredient) : ActionWeekMenuDB()
    class ChangePortionsCountDB(val data: DialogData.ChangePortionsCount) : ActionWeekMenuDB()
    class AddIngredientDB(val data: DialogData.AddIngredientPosition) : ActionWeekMenuDB()

    class MovePositionInMenuDB(
        val dateToLocalDate: LocalDate,
        val selection: CategoriesSelection,
        val position: Position
    ) : ActionWeekMenuDB()

    class DoublePositionInMenuDB(
        val dateToLocalDate: LocalDate,
        val selection: CategoriesSelection,
        val position: Position
    ) : ActionWeekMenuDB()

    data object DeleteSelected : ActionWeekMenuDB()
    class Delete(val position: Position) : ActionWeekMenuDB()
    class AddEmptyDay(val data: LocalDate) : ActionWeekMenuDB()
}