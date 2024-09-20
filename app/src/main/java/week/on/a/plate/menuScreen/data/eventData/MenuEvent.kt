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
    data object GetSelIdAndCreate : MenuEvent()
    class ChangeWeek(val date: LocalDate) : MenuEvent()
    data object ChooseWeek: MenuEvent()
    data class CreatePosition(val selId:Long): MenuEvent()
    data class EditPosition(val position: Position): MenuEvent()
    data object SelectedToShopList : MenuEvent()
    data class RecipeToShopList(val recipe: Position.PositionRecipeView): MenuEvent()
  //  data class DeleteAsk(): MenuEvent()
}




