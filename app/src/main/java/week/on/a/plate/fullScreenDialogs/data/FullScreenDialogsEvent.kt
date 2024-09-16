package week.on.a.plate.fullScreenDialogs.data

import week.on.a.plate.core.data.week.CategoriesSelection
import week.on.a.plate.menuScreen.data.eventData.ActionMenuDBData
import week.on.a.plate.menuScreen.data.eventData.DialogData
import java.time.LocalDate

sealed class FullScreenDialogsEvent {
    data class NavigateToMenuForCreate( val dateToLocalDate: LocalDate, val categoriesSelection: CategoriesSelection) : FullScreenDialogsEvent()
    data object NavigateBack : FullScreenDialogsEvent()
    data class ActionMenuBD(val data: ActionMenuDBData) : FullScreenDialogsEvent()
    data object CloseDialog : FullScreenDialogsEvent()
    data class OpenDialog(val dialog: DialogData) : FullScreenDialogsEvent()
    data class ShowSnackBar(val messageError: String) : FullScreenDialogsEvent()
    data class GetSelIdAndCreate(
        val eventAfter: (Long) -> Unit,
        val dateToLocalDate: LocalDate,
        val categoriesSelection: CategoriesSelection
    ) : FullScreenDialogsEvent()
}