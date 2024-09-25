package week.on.a.plate.mainActivity.event

import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.screenMenu.event.ActionWeekMenuDB
import java.time.LocalDate


sealed class MainEvent : Event() {
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
    data object HideDialog : MainEvent()
    class ShowDialog(val dialog: DialogViewModel) : MainEvent()
    class VoiceToText(val use:(ArrayList<String>?)->Unit) : MainEvent()
}




