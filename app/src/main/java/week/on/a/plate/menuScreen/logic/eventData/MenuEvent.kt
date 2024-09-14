package week.on.a.plate.menuScreen.logic.eventData

import week.on.a.plate.core.data.week.CategoriesSelection
import java.time.LocalDate

sealed class MenuEvent {
    data object SwitchEditMode : MenuEvent()
    data object SwitchWeekOrDayView : MenuEvent()
    data object CloseDialog : MenuEvent()
    class OpenDialog(val dialog: DialogMenuData) : MenuEvent()
    class NavigateFromMenu(val navData: NavFromMenuData) : MenuEvent()
    class ActionDBMenu(val actionDBData: ActionDBData) : MenuEvent()
    class ActionSelect(val selectedData: SelectedData) : MenuEvent()
    data class GetSelIdAndCreate(
        val action: (Long) -> Unit,
        val dateToLocalDate: LocalDate,
        val categoriesSelection: CategoriesSelection
    ) : MenuEvent()
    class ChangeWeek(val date: LocalDate) : MenuEvent()
    class ShowSnackBar(val message: String) : MenuEvent()
}




