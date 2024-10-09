package week.on.a.plate.screenMenu.event

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.SelectionView
import java.time.LocalDate

sealed class MenuEvent : Event() {
    data object SwitchEditMode : MenuEvent()
    data object SwitchWeekOrDayView : MenuEvent()
    data class NavigateFromMenu (val navData: NavFromMenuData) : MenuEvent()
    data class ActionDBMenu (val actionWeekMenuDB: ActionWeekMenuDB) : MenuEvent()
    data class ActionSelect (val selectedEvent: SelectedEvent) : MenuEvent()
    data object GetSelIdAndCreate : MenuEvent()
    data class ChangeWeek (val date: LocalDate) : MenuEvent()
    data object ChooseWeek : MenuEvent()
    data class CreatePosition (val selId: Long) : MenuEvent()
    data class CreateFirstNonPosedPosition(val date: LocalDate, val name: String) : MenuEvent()
    data class EditPosition (val position: Position) : MenuEvent()
    data object SelectedToShopList : MenuEvent()
    data object DeleteSelected : MenuEvent()
    data object CreateWeekSelIdAndCreatePosition : MenuEvent()
    data class RecipeToShopList (val recipe: Position.PositionRecipeView) : MenuEvent()
    data class SearchByDraft (val draft: Position.PositionDraftView) : MenuEvent()
    data class FindReplaceRecipe (val recipe: Position.PositionRecipeView) : MenuEvent()
    data class NavToAddRecipe (val selId: Long) : MenuEvent()
    data class EditOrDeleteSelection (val sel: SelectionView) : MenuEvent()
    data class CreateSelection (val date: LocalDate, val isForWeek:Boolean) : MenuEvent()
    //  data class DeleteAsk(): MenuEvent()
}




