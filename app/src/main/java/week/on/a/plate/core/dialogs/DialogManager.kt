package week.on.a.plate.core.dialogs

import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.dialogs.menu.addPosition.event.AddPositionEvent
import week.on.a.plate.core.dialogs.menu.addPosition.logic.AddPositionViewModel
import week.on.a.plate.core.dialogs.menu.changePositionCount.event.ChangePortionsCountEvent
import week.on.a.plate.core.dialogs.menu.changePositionCount.logic.ChangePortionsCountViewModel
import week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.core.dialogs.menu.datePicker.event.DatePickerEvent
import week.on.a.plate.core.dialogs.menu.datePicker.logic.DatePickerViewModel
import week.on.a.plate.core.dialogs.menu.editNote.event.EditNoteEvent
import week.on.a.plate.core.dialogs.menu.editNote.logic.EditNoteViewModel
import week.on.a.plate.core.dialogs.menu.editOtherPosition.event.EditOtherPositionEvent
import week.on.a.plate.core.dialogs.menu.editOtherPosition.logic.EditOtherPositionViewModel
import week.on.a.plate.core.dialogs.menu.editPositionIngredient.event.EditPositionIngredientEvent
import week.on.a.plate.core.dialogs.menu.editPositionIngredient.logic.EditPositionIngredientViewModel
import week.on.a.plate.core.dialogs.menu.editRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.core.dialogs.menu.editRecipePosition.logic.EditRecipePositionViewModel
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
            is EditOtherPositionEvent -> if (activeDialog.value is EditOtherPositionViewModel) {
                (activeDialog.value as EditOtherPositionViewModel).onEvent(event)
            }
            is AddPositionEvent -> if (activeDialog.value is AddPositionViewModel) {
                (activeDialog.value as AddPositionViewModel).onEvent(event)
            }
            is EditNoteEvent -> if (activeDialog.value is EditNoteViewModel) {
                (activeDialog.value as EditNoteViewModel).onEvent(event)
            }
            is ChangePortionsCountEvent -> if (activeDialog.value is ChangePortionsCountViewModel) {
                (activeDialog.value as ChangePortionsCountViewModel).onEvent(event)
            }
            is EditPositionIngredientEvent -> if (activeDialog.value is EditPositionIngredientViewModel) {
                (activeDialog.value as EditPositionIngredientViewModel).onEvent(event)
            }
            is DatePickerEvent -> if (activeDialog.value is DatePickerViewModel) {
                (activeDialog.value as DatePickerViewModel).onEvent(event)
            }
        }
    }
}