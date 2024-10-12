package week.on.a.plate.screens.menu.event

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.SelectionView
import java.time.LocalDate

sealed class MenuEvent : Event() {
    data object SwitchEditMode : week.on.a.plate.screens.menu.event.MenuEvent()
    data object SwitchWeekOrDayView : week.on.a.plate.screens.menu.event.MenuEvent()
    data class NavigateFromMenu (val navData: week.on.a.plate.screens.menu.event.NavFromMenuData) : week.on.a.plate.screens.menu.event.MenuEvent()
    data class ActionDBMenu (val actionWeekMenuDB: week.on.a.plate.screens.menu.event.ActionWeekMenuDB) : week.on.a.plate.screens.menu.event.MenuEvent()
    data class ActionSelect (val selectedEvent: week.on.a.plate.screens.menu.event.SelectedEvent) : week.on.a.plate.screens.menu.event.MenuEvent()
    data object GetSelIdAndCreate : week.on.a.plate.screens.menu.event.MenuEvent()
    data class ChangeWeek (val date: LocalDate) : week.on.a.plate.screens.menu.event.MenuEvent()
    data object ChooseWeek : week.on.a.plate.screens.menu.event.MenuEvent()
    data class CreatePosition (val selId: Long) : week.on.a.plate.screens.menu.event.MenuEvent()
    data class CreateFirstNonPosedPosition(val date: LocalDate, val name: String) : week.on.a.plate.screens.menu.event.MenuEvent()
    data class EditPositionMore (val position: Position) : week.on.a.plate.screens.menu.event.MenuEvent()
    data class EditOtherPosition (val position: Position) : week.on.a.plate.screens.menu.event.MenuEvent()
    data object SelectedToShopList : week.on.a.plate.screens.menu.event.MenuEvent()
    data object DeleteSelected : week.on.a.plate.screens.menu.event.MenuEvent()
    data object CreateWeekSelIdAndCreatePosition : week.on.a.plate.screens.menu.event.MenuEvent()
    data class RecipeToShopList (val recipe: Position.PositionRecipeView) : week.on.a.plate.screens.menu.event.MenuEvent()
    data class SearchByDraft (val draft: Position.PositionDraftView) : week.on.a.plate.screens.menu.event.MenuEvent()
    data class FindReplaceRecipe (val recipe: Position.PositionRecipeView) : week.on.a.plate.screens.menu.event.MenuEvent()
    data class NavToAddRecipe (val selId: Long) : week.on.a.plate.screens.menu.event.MenuEvent()
    data class EditOrDeleteSelection (val sel: SelectionView) : week.on.a.plate.screens.menu.event.MenuEvent()
    data class CreateSelection (val date: LocalDate, val isForWeek:Boolean) : week.on.a.plate.screens.menu.event.MenuEvent()
    //  data class DeleteAsk(): MenuEvent()
}




