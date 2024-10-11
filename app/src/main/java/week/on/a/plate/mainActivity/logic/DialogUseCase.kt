package week.on.a.plate.mainActivity.logic

import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.dialogAddIngredient.event.AddIngredientEvent
import week.on.a.plate.dialogAddIngredient.logic.AddIngredientViewModel
import week.on.a.plate.dialogAddTag.event.AddTagEvent
import week.on.a.plate.dialogAddTag.logic.AddTagViewModel
import week.on.a.plate.screenFilters.dialogs.filterVoiceApply.event.FilterVoiceApplyEvent
import week.on.a.plate.screenFilters.dialogs.filterVoiceApply.logic.FilterVoiceApplyViewModel
import week.on.a.plate.dialogAddPosition.event.AddPositionEvent
import week.on.a.plate.dialogAddPosition.logic.AddPositionViewModel
import week.on.a.plate.dialogChangePositionCount.event.ChangePortionsCountEvent
import week.on.a.plate.dialogChangePositionCount.logic.ChangePortionsCountViewModel
import week.on.a.plate.dialogChooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.dialogChooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.dialogDatePicker.event.DatePickerEvent
import week.on.a.plate.dialogDatePicker.logic.DatePickerViewModel
import week.on.a.plate.dialogEditOneString.event.EditOneStringEvent
import week.on.a.plate.dialogEditOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogEditOtherPosition.event.EditOtherPositionEvent
import week.on.a.plate.dialogEditOtherPosition.logic.EditOtherPositionViewModel
import week.on.a.plate.dialogEditPositionIngredient.event.EditPositionIngredientEvent
import week.on.a.plate.dialogEditPositionIngredient.logic.EditPositionIngredientViewModel
import week.on.a.plate.dialogEditRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.dialogEditRecipePosition.logic.EditRecipePositionViewModel
import week.on.a.plate.screenFilters.dialogs.selectedFilters.event.SelectedFiltersEvent
import week.on.a.plate.screenFilters.dialogs.selectedFilters.logic.SelectedFiltersViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.dialogEditOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.dialogEditOrDelete.logic.EditOrDeleteViewModel
import week.on.a.plate.dialogEditSelection.event.EditSelectionEvent
import week.on.a.plate.dialogEditSelection.logic.EditSelectionViewModel
import week.on.a.plate.dialogTimePick.event.TimePickEvent
import week.on.a.plate.dialogTimePick.logic.TimePickViewModel
import java.util.Stack
import javax.inject.Inject

class DialogUseCase @Inject constructor() {
    val activeDialog = mutableStateOf<DialogViewModel?>(null)
    private val hiddenDialog = Stack<DialogViewModel>()
    private val dialogsVMMap = Stack<DialogViewModel>()

    private fun showTopDialog(dialog: DialogViewModel) {
        activeDialog.value = dialog
    }

    private fun hideAllDialogs() {
        activeDialog.value = null
        hiddenDialog.clear()
    }

    fun closeDialog() {
        if (dialogsVMMap.isNotEmpty()) {
            dialogsVMMap.pop()
            if (dialogsVMMap.isNotEmpty()) {
                val next = dialogsVMMap.peek()
                if (next != hiddenDialog){
                    showTopDialog(next)
                }else{
                    hideAllDialogs()
                }
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
            is EditOneStringEvent -> if (activeDialog.value is EditOneStringViewModel) {
                (activeDialog.value as EditOneStringViewModel).onEvent(event)
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
            is AddTagEvent -> if (activeDialog.value is AddTagViewModel) {
                (activeDialog.value as AddTagViewModel).onEvent(event)
            }
            is AddIngredientEvent -> if (activeDialog.value is AddIngredientViewModel) {
                (activeDialog.value as AddIngredientViewModel).onEvent(event)
            }
            is SelectedFiltersEvent -> if (activeDialog.value is SelectedFiltersViewModel) {
                (activeDialog.value as SelectedFiltersViewModel).onEvent(event)
            }
            is FilterVoiceApplyEvent -> if (activeDialog.value is FilterVoiceApplyViewModel) {
                (activeDialog.value as FilterVoiceApplyViewModel).onEvent(event)
            }
            is TimePickEvent -> if (activeDialog.value is TimePickViewModel) {
                (activeDialog.value as TimePickViewModel).onEvent(event)
            }
            is EditOrDeleteEvent -> if (activeDialog.value is EditOrDeleteViewModel) {
                (activeDialog.value as EditOrDeleteViewModel).onEvent(event)
            }
            is EditSelectionEvent -> if (activeDialog.value is EditSelectionViewModel) {
                (activeDialog.value as EditSelectionViewModel).onEvent(event)
            }
        }
    }

    fun hide() {
        hiddenDialog.add(activeDialog.value)
        activeDialog.value = null
    }

    fun show() {
        if (hiddenDialog.isNotEmpty()) activeDialog.value = hiddenDialog.pop()
    }
}