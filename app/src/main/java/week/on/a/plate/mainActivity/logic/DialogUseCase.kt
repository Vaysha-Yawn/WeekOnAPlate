package week.on.a.plate.mainActivity.logic

import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.dialogsAddFilters.addCategory.event.AddCategoryEvent
import week.on.a.plate.dialogsAddFilters.addCategory.logic.AddCategoryViewModel
import week.on.a.plate.dialogsAddFilters.addIngrdient.event.AddIngredientEvent
import week.on.a.plate.dialogsAddFilters.addIngrdient.logic.AddIngredientViewModel
import week.on.a.plate.dialogsAddFilters.addTag.event.AddTagEvent
import week.on.a.plate.dialogsAddFilters.addTag.logic.AddTagViewModel
import week.on.a.plate.screenFilters.dialogs.filterVoiceApply.event.FilterVoiceApplyEvent
import week.on.a.plate.screenFilters.dialogs.filterVoiceApply.logic.FilterVoiceApplyViewModel
import week.on.a.plate.screenMenu.dialogs.addPosition.event.AddPositionEvent
import week.on.a.plate.screenMenu.dialogs.addPosition.logic.AddPositionViewModel
import week.on.a.plate.screenMenu.dialogs.changePositionCount.event.ChangePortionsCountEvent
import week.on.a.plate.screenMenu.dialogs.changePositionCount.logic.ChangePortionsCountViewModel
import week.on.a.plate.screenMenu.dialogs.chooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.screenMenu.dialogs.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.screenMenu.dialogs.datePicker.event.DatePickerEvent
import week.on.a.plate.screenMenu.dialogs.datePicker.logic.DatePickerViewModel
import week.on.a.plate.screenMenu.dialogs.editNote.event.EditNoteEvent
import week.on.a.plate.screenMenu.dialogs.editNote.logic.EditNoteViewModel
import week.on.a.plate.screenMenu.dialogs.editOtherPosition.event.EditOtherPositionEvent
import week.on.a.plate.screenMenu.dialogs.editOtherPosition.logic.EditOtherPositionViewModel
import week.on.a.plate.screenMenu.dialogs.editPositionIngredient.event.EditPositionIngredientEvent
import week.on.a.plate.screenMenu.dialogs.editPositionIngredient.logic.EditPositionIngredientViewModel
import week.on.a.plate.screenMenu.dialogs.editRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.screenMenu.dialogs.editRecipePosition.logic.EditRecipePositionViewModel
import week.on.a.plate.screenFilters.dialogs.selectedFilters.event.SelectedFiltersEvent
import week.on.a.plate.screenFilters.dialogs.selectedFilters.logic.SelectedFiltersViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.screenCreateRecipe.timePickDialog.event.TimePickEvent
import week.on.a.plate.screenCreateRecipe.timePickDialog.logic.TimePickViewModel
import java.util.Stack
import javax.inject.Inject

class DialogUseCase @Inject constructor() {
    val activeDialog = mutableStateOf<DialogViewModel?>(null)
    private var hiddenDialog: DialogViewModel? = null
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
            is AddCategoryEvent -> if (activeDialog.value is AddCategoryViewModel) {
                (activeDialog.value as AddCategoryViewModel).onEvent(event)
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
        }
    }

    fun hide() {
        hiddenDialog = activeDialog.value
        hideAllDialogs()
    }

    fun show() {
        activeDialog.value = hiddenDialog
        hiddenDialog = null
    }
}