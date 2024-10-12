package week.on.a.plate.screens.menu.event

import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale

sealed class ActionWeekMenuDB {
    class EditNoteDB(val data: Position.PositionNoteView) : week.on.a.plate.screens.menu.event.ActionWeekMenuDB()

    class AddNoteDB(val text: String, val selId: Long,) : week.on.a.plate.screens.menu.event.ActionWeekMenuDB()
    class AddRecipePositionInMenuDB(
        val selId: Long,
        val recipe: Position.PositionRecipeView
    ) : week.on.a.plate.screens.menu.event.ActionWeekMenuDB()
    class AddIngredientPositionDB(
        val updatedPosition: Position.PositionIngredientView,
        val selId: Long,
    ) : week.on.a.plate.screens.menu.event.ActionWeekMenuDB()


    class EditIngredientPositionDB(val updatedPosition: Position.PositionIngredientView) :
        week.on.a.plate.screens.menu.event.ActionWeekMenuDB()

    class ChangePortionsCountDB(val recipe: Position.PositionRecipeView, val count: Int) :
        week.on.a.plate.screens.menu.event.ActionWeekMenuDB()

    class MovePositionInMenuDB(
        val selId: Long,
        val position: Position
    ) : week.on.a.plate.screens.menu.event.ActionWeekMenuDB()

    class DoublePositionInMenuDB(
        val selId: Long,
        val position: Position
    ) : week.on.a.plate.screens.menu.event.ActionWeekMenuDB()

    class Delete(val position: Position) : week.on.a.plate.screens.menu.event.ActionWeekMenuDB()
    class AddDraft(val draft: Position.PositionDraftView) : week.on.a.plate.screens.menu.event.ActionWeekMenuDB()
    class EditDraft(
        val oldDraft: Position.PositionDraftView,
        val filters: Pair<List<RecipeTagView>, List<IngredientView>>
    ) : week.on.a.plate.screens.menu.event.ActionWeekMenuDB()

    class DeleteSelection(val sel: SelectionView) : week.on.a.plate.screens.menu.event.ActionWeekMenuDB()
    class EditSelection(val sel: SelectionView, val newName: String, val time: LocalTime) : week.on.a.plate.screens.menu.event.ActionWeekMenuDB()
    class CreateSelection(val date: LocalDate, val newName: String, val locale: Locale,
                          val isForWeek: Boolean, val time:LocalTime) : week.on.a.plate.screens.menu.event.ActionWeekMenuDB()
}