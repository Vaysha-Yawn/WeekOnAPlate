package week.on.a.plate.screens.menu.event

import android.content.Context
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.screens.wrapperDatePicker.event.WrapperDatePickerEvent
import java.time.LocalDate

sealed class MenuEvent : Event() {
    data class NavigateFromMenu(val navData: NavFromMenuData) : MenuEvent()
    data class ActionDBMenu(val actionWeekMenuDB: ActionWeekMenuDB) : MenuEvent()
    data class ActionSelect(val selectedEvent: SelectedEvent) : MenuEvent()
    data class ActionWrapperDatePicker(val event: WrapperDatePickerEvent) : MenuEvent()
    data class GetSelIdAndCreate(val context: Context) : MenuEvent()
    data class CreatePosition(val selId: Long, val context: Context) : MenuEvent()
    data class CreateFirstNonPosedPosition(val date: LocalDate, val name: String, val context: Context) : MenuEvent()
    data class EditPositionMore(val position: Position) : MenuEvent()
    data class EditOtherPosition(val position: Position) : MenuEvent()
    data class CreateWeekSelIdAndCreatePosition(val context: Context) : MenuEvent()
    data class RecipeToShopList(val recipe: Position.PositionRecipeView) : MenuEvent()
    data class SearchByDraft(val draft: Position.PositionDraftView) : MenuEvent()
    data class FindReplaceRecipe(val recipe: Position.PositionRecipeView) : MenuEvent()
    data class NavToAddRecipe(val selId: Long, val context:Context) : MenuEvent()
    data class EditOrDeleteSelection(val sel: SelectionView) : MenuEvent()
    data class CreateSelection(val date: LocalDate, val isForWeek: Boolean) : MenuEvent()
    data object SelectedToShopList : MenuEvent()
    data object DeleteSelected : MenuEvent()
}




