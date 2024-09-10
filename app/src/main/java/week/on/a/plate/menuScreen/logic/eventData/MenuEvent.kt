package week.on.a.plate.menuScreen.logic.eventData

import week.on.a.plate.core.data.week.Position

sealed class MenuEvent {
    class AddRecipeToShoppingList(val recipe: Position.PositionRecipeView) : MenuEvent()
    data object SwitchEditMode : MenuEvent()
    class CheckRecipe(val id: Long) : MenuEvent()
    data object SwitchWeekOrDayView : MenuEvent()
    class AddCheckState(val id: Long) : MenuEvent()
    data object ChooseAll : MenuEvent()
    data object DeleteSelected : MenuEvent()
    data object CloseDialog : MenuEvent()
    class OpenDialog(val dialog: DialogMenuData) : MenuEvent()
    class NavigateFromMenu(val navData: NavFromMenuData) : MenuEvent()
    class ActionDBMenu(val actionDBData: ActionDBData) : MenuEvent()
    data object SelectedToShopList : MenuEvent()
}




