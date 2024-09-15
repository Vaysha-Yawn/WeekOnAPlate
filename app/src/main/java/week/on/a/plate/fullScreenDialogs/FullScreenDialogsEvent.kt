package week.on.a.plate.fullScreenDialogs

import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.menuScreen.logic.eventData.ActionMenuDBData
import week.on.a.plate.menuScreen.logic.eventData.DialogData
import java.time.LocalDate

sealed class FullScreenDialogsEvent {
    data object Navigate : FullScreenDialogsEvent()
    data object NavigateBack : FullScreenDialogsEvent()
    data class ActionMenuBD(val data: ActionMenuDBData) : FullScreenDialogsEvent()
    data object CloseDialog : FullScreenDialogsEvent()
    class OpenDialog(val dialog: DialogData) : FullScreenDialogsEvent()
    class GetSelIdAndCreate(
        val action: (Long) -> Unit,
        val dateToLocalDate: LocalDate,
        val categoriesSelection: CategoriesSelection
    ) : FullScreenDialogsEvent()
    class ShowSnackBar(val messageError: String) : FullScreenDialogsEvent()
}