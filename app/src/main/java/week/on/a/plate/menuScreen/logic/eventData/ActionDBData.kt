package week.on.a.plate.menuScreen.logic.eventData

import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.core.data.week.Position
import java.time.LocalDate

sealed class ActionDBData{
    class EditNoteDB(val data: DialogMenuData.EditNote) : ActionDBData()
    class AddNoteDB(val data: DialogMenuData.AddNote) : ActionDBData()

    class AddRecipePositionInMenuDB(
        val selId:Long,
        val recipe: Position.PositionRecipeView
    ) : ActionDBData()

    class EditIngredientDB(val data: DialogMenuData.EditIngredient) : ActionDBData()
    class ChangePortionsCountDB(val data: DialogMenuData.ChangePortionsCount) : ActionDBData()
    class AddIngredientDB(val data: DialogMenuData.AddIngredient) : ActionDBData()

    class MovePositionInMenuDB(
        val dateToLocalDate: LocalDate,
        val selection: CategoriesSelection,
        val position: Position
    ) : ActionDBData()

    class DoublePositionInMenuDB(
        val dateToLocalDate: LocalDate,
        val selection: CategoriesSelection,
        val position: Position
    ) : ActionDBData()

    data object DeleteSelected : ActionDBData()
    class Delete(val position: Position) : ActionDBData()
    class AddEmptyDay(val data: LocalDate) : ActionDBData()
}