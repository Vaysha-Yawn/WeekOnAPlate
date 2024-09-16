package week.on.a.plate.menuScreen.data.eventData

import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.core.data.week.Position
import java.time.LocalDate

sealed class ActionMenuDBData{
    class EditNoteDB(val data: DialogData.EditNote) : ActionMenuDBData()
    class AddNoteDB(val text:String, val selectionId:Long) : ActionMenuDBData()

    class AddRecipePositionInMenuDB(
        val selId:Long,
        val recipe: Position.PositionRecipeView
    ) : ActionMenuDBData()

    class EditIngredientDB(val data: DialogData.EditIngredient) : ActionMenuDBData()
    class ChangePortionsCountDB(val data: DialogData.ChangePortionsCount) : ActionMenuDBData()
    class AddIngredientDB(val data: DialogData.AddIngredient) : ActionMenuDBData()

    class MovePositionInMenuDB(
        val dateToLocalDate: LocalDate,
        val selection: CategoriesSelection,
        val position: Position
    ) : ActionMenuDBData()

    class DoublePositionInMenuDB(
        val dateToLocalDate: LocalDate,
        val selection: CategoriesSelection,
        val position: Position
    ) : ActionMenuDBData()

    data object DeleteSelected : ActionMenuDBData()
    class Delete(val position: Position) : ActionMenuDBData()
    class AddEmptyDay(val data: LocalDate) : ActionMenuDBData()
}