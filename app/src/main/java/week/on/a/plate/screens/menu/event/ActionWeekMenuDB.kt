package week.on.a.plate.screens.menu.event

import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale

sealed class ActionWeekMenuDB {
    class EditNoteDB(val data: Position.PositionNoteView) : ActionWeekMenuDB()

    class AddNoteDB(val text: String, val selId: Long,) : ActionWeekMenuDB()

    class AddRecipePositionInMenuDB(
        val selId: Long,
        val recipe: Position.PositionRecipeView
    ) : ActionWeekMenuDB()

    class AddIngredientPositionDB(
        val updatedPosition: Position.PositionIngredientView,
        val selId: Long,
    ) : ActionWeekMenuDB()

    class EditIngredientPositionDB(val updatedPosition: Position.PositionIngredientView) :
        ActionWeekMenuDB()

    class ChangePortionsCountDB(val recipe: Position.PositionRecipeView, val count: Int) :
        ActionWeekMenuDB()

    class MovePositionInMenuDB(
        val selId: Long,
        val position: Position
    ) : ActionWeekMenuDB()

    class DoublePositionInMenuDB(
        val selId: Long,
        val position: Position
    ) : ActionWeekMenuDB()

    class Delete(val position: Position) : ActionWeekMenuDB()
    class AddDraft(val draft: Position.PositionDraftView) : ActionWeekMenuDB()
    class EditDraft(
        val oldDraft: Position.PositionDraftView,
        val filters: Pair<List<RecipeTagView>, List<IngredientView>>
    ) : ActionWeekMenuDB()

    class DeleteSelection(val sel: SelectionView) : ActionWeekMenuDB()
    class EditSelection(val sel: SelectionView, val newName: String, val time: LocalTime) : ActionWeekMenuDB()
    class CreateSelection(val date: LocalDate, val newName: String, val locale: Locale,
                          val isForWeek: Boolean, val time:LocalTime) : ActionWeekMenuDB()
}