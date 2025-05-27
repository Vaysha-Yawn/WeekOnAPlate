package week.on.a.plate.core.dialogCore

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.Event
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
import week.on.a.plate.dialogs.calendarMy.event.CalendarMyEvent
import week.on.a.plate.dialogs.changePortions.event.ChangePortionsCountEvent
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.dialogs.chooseIngredientsForStep.event.ChooseIngredientsForStepEvent
import week.on.a.plate.dialogs.chooseIngredientsForStep.logic.ChooseIngredientsForStepViewModel
import week.on.a.plate.dialogs.chooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.dialogs.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.dialogs.cookStepMore.event.CookStepMoreEvent
import week.on.a.plate.dialogs.cookStepMore.logic.CookStepMoreDialogViewModel
import week.on.a.plate.dialogs.datePick.event.DatePickerEvent
import week.on.a.plate.dialogs.datePick.logic.DatePickerViewModel
import week.on.a.plate.dialogs.editIngredientInMenu.event.EditPositionIngredientEvent
import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
import week.on.a.plate.dialogs.editOneString.event.EditOneStringEvent
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.dialogs.editOrDelete.logic.EditOrDeleteViewModel
import week.on.a.plate.dialogs.editOtherPositionMoreDialog.event.OtherPositionMoreEvent
import week.on.a.plate.dialogs.editOtherPositionMoreDialog.logic.EditOtherPositionViewModel
import week.on.a.plate.dialogs.editPositionRecipeMoreDialog.event.ActionMoreRecipePositionEvent
import week.on.a.plate.dialogs.editPositionRecipeMoreDialog.logic.EditRecipePositionViewModel
import week.on.a.plate.dialogs.editSelectionDialog.event.EditSelectionEvent
import week.on.a.plate.dialogs.editSelectionDialog.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.event.ChooseHowImagePickEvent
import week.on.a.plate.dialogs.forCreateRecipeScreen.chooseHowImagePick.logic.ChooseHowImagePickViewModel
import week.on.a.plate.dialogs.forCreateRecipeScreen.exitApply.event.ExitApplyEvent
import week.on.a.plate.dialogs.forCreateRecipeScreen.exitApply.logic.ExitApplyViewModel
import week.on.a.plate.dialogs.forSearchScreen.filtersMore.event.FiltersMoreEvent
import week.on.a.plate.dialogs.forSearchScreen.filtersMore.logic.FiltersMoreViewModel
import week.on.a.plate.dialogs.forSearchScreen.sortMore.event.SortMoreEvent
import week.on.a.plate.dialogs.forSearchScreen.sortMore.logic.SortMoreDialogViewModel
import week.on.a.plate.dialogs.forSettingsScreen.setPermanentMeals.event.SetPermanentMealsEvent
import week.on.a.plate.dialogs.forSettingsScreen.setPermanentMeals.logic.SetPermanentMealsViewModel
import week.on.a.plate.dialogs.forSettingsScreen.setTheme.event.SetThemeEvent
import week.on.a.plate.dialogs.forSettingsScreen.setTheme.logic.SetThemesViewModel
import week.on.a.plate.dialogs.timePick.event.TimePickEvent
import week.on.a.plate.dialogs.timePick.logic.TimePickViewModel
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateIngredient.event.AddIngredientEvent
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateIngredient.logic.AddIngredientViewModel
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateTag.event.AddTagEvent
import week.on.a.plate.screens.additional.filters.dialogs.editOrCreateTag.logic.AddTagViewModel
import week.on.a.plate.screens.additional.filters.dialogs.filterVoiceApply.event.FilterVoiceApplyEvent
import week.on.a.plate.screens.additional.filters.dialogs.filterVoiceApply.logic.FilterVoiceApplyViewModel
import week.on.a.plate.screens.additional.filters.dialogs.selectedFilters.event.SelectedFiltersEvent
import week.on.a.plate.screens.additional.filters.dialogs.selectedFilters.logic.SelectedFiltersViewModel
import java.util.Stack
import javax.inject.Inject

class DialogUseCase @Inject constructor() {
    private val activeDialog = mutableStateOf<DialogViewModel<*>?>(null)
    val _activeDialog: State<DialogViewModel<*>?> = activeDialog
    private val hiddenDialog = Stack<DialogViewModel<*>>()
    private val dialogsStack = Stack<DialogViewModel<*>>()

    private fun showTopDialog(dialog: DialogViewModel<*>) {
        activeDialog.value = dialog
    }

    private fun hideAllDialogs() {
        activeDialog.value = null
        hiddenDialog.clear()
    }

    fun closeDialog() {
        if (dialogsStack.isNotEmpty()) {
            dialogsStack.pop()
            if (dialogsStack.isNotEmpty()) {
                val next = dialogsStack.peek()
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

    fun openDialog(vm: DialogViewModel<*>) {
        dialogsStack.add(vm)
        showTopDialog(vm)
    }

    fun onEvent(event: Event) {
        when (event) {
            is ChooseWeekDialogEvent -> if (activeDialog.value is ChooseWeekViewModel) {
                (activeDialog.value as ChooseWeekViewModel).onEvent(event)
            }
            is CalendarMyEvent -> if (activeDialog.value is ChooseWeekViewModel) {
                (activeDialog.value as ChooseWeekViewModel).onEvent(event)
            }
            is ActionMoreRecipePositionEvent -> if (activeDialog.value is EditRecipePositionViewModel) {
                (activeDialog.value as EditRecipePositionViewModel).onEvent(event)
            }
            is OtherPositionMoreEvent -> if (activeDialog.value is EditOtherPositionViewModel) {
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
            is SortMoreEvent -> if (activeDialog.value is SortMoreDialogViewModel) {
                (activeDialog.value as SortMoreDialogViewModel).onEvent(event)
            }
            is FiltersMoreEvent -> if (activeDialog.value is FiltersMoreViewModel) {
                (activeDialog.value as FiltersMoreViewModel).onEvent(event)
            }
            is CookStepMoreEvent -> if (activeDialog.value is CookStepMoreDialogViewModel) {
                (activeDialog.value as CookStepMoreDialogViewModel).onEvent(event)
            }
            is ChooseIngredientsForStepEvent -> if (activeDialog.value is ChooseIngredientsForStepViewModel) {
                (activeDialog.value as ChooseIngredientsForStepViewModel).onEvent(event)
            }
            is ExitApplyEvent -> if (activeDialog.value is ExitApplyViewModel) {
                (activeDialog.value as ExitApplyViewModel).onEvent(event)
            }
            is ChooseHowImagePickEvent -> if (activeDialog.value is ChooseHowImagePickViewModel) {
                (activeDialog.value as ChooseHowImagePickViewModel).onEvent(event)
            }
            is SetThemeEvent -> if (activeDialog.value is SetThemesViewModel) {
                (activeDialog.value as SetThemesViewModel).onEvent(event)
            }
            is SetPermanentMealsEvent -> if (activeDialog.value is SetPermanentMealsViewModel) {
                (activeDialog.value as SetPermanentMealsViewModel).onEvent(event)
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