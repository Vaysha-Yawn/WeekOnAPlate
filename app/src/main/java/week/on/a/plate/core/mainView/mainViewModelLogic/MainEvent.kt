package week.on.a.plate.core.mainView.mainViewModelLogic

import androidx.lifecycle.ViewModel
import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.core.dialogs.DialogType
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.menuScreen.data.eventData.ActionWeekMenuDB
import java.time.LocalDate


abstract class Event

sealed class MainEvent:Event() {
    data object CloseDialog : MainEvent()
    class OpenDialog(val dialog: DialogViewModel) : MainEvent()
    class ActionDBMenu(val actionMenuDBData: ActionWeekMenuDB) : MainEvent()
    data class GetSelIdAndCreate(
        val action: (Long) -> Unit,
        val dateToLocalDate: LocalDate,
        val categoriesSelection: CategoriesSelection
    ) : MainEvent()
    class ShowSnackBar(val message: String) : MainEvent()
    class Navigate(val destination: Any) : MainEvent()
    data object NavigateBack : MainEvent()
}




