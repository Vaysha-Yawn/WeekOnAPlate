package week.on.a.plate.menuScreen.logic

import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.RecipeShortView
import java.time.LocalDate

sealed class MenuEvent {
    data object SwitchWeekOrDayView : MenuEvent()
    class AddCheckState(val id: Long) : MenuEvent()
    class NavToFullRecipe(val rec: RecipeShortView) : MenuEvent()
    data object SwitchEditMode : MenuEvent()
    class CheckRecipe(val id: Long) : MenuEvent()
    class AddRecipeToCategory(val date: LocalDate, val category: String) : MenuEvent()
    class RecipeToNextStep(val id: Long) : MenuEvent()
    class Edit(val id: Position) : MenuEvent()
    class Search(val draft: Position.PositionDraftView) : MenuEvent()
    class AddEmptyDay(val data: LocalDate) : MenuEvent()
    class AddRecipeToShoppingList(val recipe: Position.PositionRecipeView) : MenuEvent()
    class Delete(val recipe: Position) : MenuEvent()
    class Move(val recipe: Position) : MenuEvent()
    class ChangeCount(val recipe: Position.PositionRecipeView) : MenuEvent()
    class FindReplace(val recipe: Position.PositionRecipeView) : MenuEvent()
    class Double(val recipe: Position) : MenuEvent()
    class AddIngredientToCategory(val date: LocalDate, val category: String) : MenuEvent()
    class AddDraftToCategory(val date: LocalDate, val category: String) : MenuEvent()
    class AddNoteToCategory(val date: LocalDate, val category: String) : MenuEvent()

    data object ChooseAll : MenuEvent()
    data object DeleteSelected : MenuEvent()
    data object SelectedToShopList : MenuEvent()
}


