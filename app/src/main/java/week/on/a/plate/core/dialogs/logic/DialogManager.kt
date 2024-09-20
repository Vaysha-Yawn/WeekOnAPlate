package week.on.a.plate.core.dialogs.logic

import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.chooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.core.dialogs.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.core.dialogs.editRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.core.dialogs.editRecipePosition.logic.EditRecipePositionViewModel
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import java.util.Stack
import javax.inject.Inject

class DialogManager @Inject constructor() {
    val activeDialog = mutableStateOf<DialogViewModel?>(null)
    private val dialogsVMMap = Stack<DialogViewModel>()

    private fun showTopDialog(dialog: DialogViewModel) {
        activeDialog.value = dialog
    }

    private fun hideAllDialogs() {
        activeDialog.value = null
    }

    fun closeDialog() {
        if (dialogsVMMap.isNotEmpty()) {
            dialogsVMMap.pop()
            if (dialogsVMMap.isNotEmpty()) {
                val next = dialogsVMMap.peek()
                showTopDialog(next)
            } else {
                hideAllDialogs()
            }
        } else {
            hideAllDialogs()
        }
    }

    fun openDialog(vm: DialogViewModel) {
        dialogsVMMap.add(vm)
        showTopDialog(vm)
    }

    fun onEvent(event: Event) {
        when (event) {
            is ChooseWeekDialogEvent -> if (activeDialog.value is ChooseWeekViewModel) {
                (activeDialog.value as ChooseWeekViewModel).onEvent(event)
            }
            is EditRecipePositionEvent -> if (activeDialog.value is EditRecipePositionViewModel) {
                (activeDialog.value as EditRecipePositionViewModel).onEvent(event)
            }
        }
    }
}