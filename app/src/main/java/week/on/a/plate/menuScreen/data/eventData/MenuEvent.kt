package week.on.a.plate.menuScreen.data.eventData

import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.dialogs.DialogType
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import java.time.LocalDate

sealed class MenuEvent: Event() {
    data object SwitchEditMode : MenuEvent()
    data object SwitchWeekOrDayView : MenuEvent()
    class NavigateFromMenu(val navData: NavFromMenuData) : MenuEvent()
    class ActionDBMenu(val actionWeekMenuDB: ActionWeekMenuDB) : MenuEvent()
    class ActionSelect(val selectedData: SelectedData) : MenuEvent()
    data class GetSelIdAndCreate(
        val action: (Long) -> Unit,
        val dateToLocalDate: LocalDate,
        val categoriesSelection: CategoriesSelection
    ) : MenuEvent()
    class ChangeWeek(val date: LocalDate) : MenuEvent()

    data object ChooseWeek: MenuEvent()
    data class ChangePortionsCount(val recipe:Position.PositionRecipeView): MenuEvent()
    data class EditIngredientPosition(val ingredientPos:Position.PositionIngredientView): MenuEvent()
    data class EditNote(val note:Position.PositionNoteView): MenuEvent()
    data class EditDraft(val draft:Position.PositionDraftView): MenuEvent()
    data class CreateDraft(val selId:Long?): MenuEvent()
    data class CreateIngredientPosition(val selId:Long?): MenuEvent()
    data class CreateNote(val selId:Long?): MenuEvent()
    data class CreatePosition(val selId:Long?): MenuEvent()
    data class EditPosition(val position: Position): MenuEvent()
    data object SelectedToShopList : MenuEvent()
    data class RecipeToShopList(val recipe: Position.PositionRecipeView): MenuEvent()
  //  data class DeleteAsk(): MenuEvent()
}




