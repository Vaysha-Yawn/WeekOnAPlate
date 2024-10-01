package week.on.a.plate.screenMenu.event

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.core.Event
import java.time.LocalDate

sealed class MenuEvent : Event() {
    data object SwitchEditMode : MenuEvent()
    data object SwitchWeekOrDayView : MenuEvent()
    class NavigateFromMenu(val navData: NavFromMenuData) : MenuEvent()
    class ActionDBMenu(val actionWeekMenuDB: ActionWeekMenuDB) : MenuEvent()
    class ActionSelect(val selectedEvent: SelectedEvent) : MenuEvent()
    data object GetSelIdAndCreate : MenuEvent()
    class ChangeWeek(val date: LocalDate) : MenuEvent()
    data object ChooseWeek : MenuEvent()
    data class CreatePosition(val selId: Long) : MenuEvent()
    data class CreateFirstNonPosedPosition(val date: LocalDate) : MenuEvent()
    data class EditPosition(val position: Position) : MenuEvent()
    data object SelectedToShopList : MenuEvent()
    data object DeleteSelected : MenuEvent()
    data class RecipeToShopList(val recipe: Position.PositionRecipeView) : MenuEvent()
    class SearchByDraft(val draft: Position.PositionDraftView) : MenuEvent()
    class FindReplaceRecipe(val recipe: Position.PositionRecipeView) : MenuEvent()
    class NavToAddRecipe(val selId: Long?) : MenuEvent()
    //  data class DeleteAsk(): MenuEvent()
}




