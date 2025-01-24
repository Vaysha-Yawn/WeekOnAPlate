package week.on.a.plate.dialogs.core

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.dialogs.editOrCreateIngredient.event.AddIngredientEvent
import week.on.a.plate.dialogs.editOrCreateIngredient.logic.AddIngredientViewModel
import week.on.a.plate.dialogs.editOrCreateIngredient.view.AddIngredient
import week.on.a.plate.dialogs.editOrCreateTag.event.AddTagEvent
import week.on.a.plate.dialogs.editOrCreateTag.logic.AddTagViewModel
import week.on.a.plate.dialogs.editOrCreateTag.view.AddTag
import week.on.a.plate.screens.filters.dialogs.filterVoiceApply.event.FilterVoiceApplyEvent
import week.on.a.plate.screens.filters.dialogs.filterVoiceApply.logic.FilterVoiceApplyViewModel
import week.on.a.plate.screens.filters.dialogs.filterVoiceApply.view.DialogVoiceApplyTags
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
import week.on.a.plate.dialogs.addPositionChoose.view.AddPositionDialogContent
import week.on.a.plate.dialogs.changePortions.event.ChangePortionsCountEvent
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.dialogs.chooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.dialogs.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.dialogs.chooseWeekInMenu.state.ChooseWeekUIState
import week.on.a.plate.dialogs.dialogDatePicker.event.DatePickerEvent
import week.on.a.plate.dialogs.dialogDatePicker.logic.DatePickerViewModel
import week.on.a.plate.dialogs.dialogDatePicker.state.DatePickerUIState
import week.on.a.plate.dialogs.dialogDatePicker.view.DatePickerMy
import week.on.a.plate.dialogs.editOneString.event.EditOneStringEvent
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.view.EditOneStringContent
import week.on.a.plate.dialogs.editOtherPositionMore.event.OtherPositionMoreEvent
import week.on.a.plate.dialogs.editOtherPositionMore.logic.EditOtherPositionViewModel
import week.on.a.plate.dialogs.editOtherPositionMore.view.EditOtherPositionDialogContent
import week.on.a.plate.dialogs.editIngredientInMenu.event.EditPositionIngredientEvent
import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
import week.on.a.plate.dialogs.editIngredientInMenu.view.EditOrAddIngredientBottomDialogContent
import week.on.a.plate.dialogs.editPositionRecipeMore.event.ActionMoreRecipePositionEvent
import week.on.a.plate.dialogs.editPositionRecipeMore.logic.EditRecipePositionViewModel
import week.on.a.plate.dialogs.editPositionRecipeMore.view.EditRecipePositionDialogContent
import week.on.a.plate.screens.filters.dialogs.selectedFilters.event.SelectedFiltersEvent
import week.on.a.plate.screens.filters.dialogs.selectedFilters.logic.SelectedFiltersViewModel
import week.on.a.plate.screens.filters.dialogs.selectedFilters.view.DialogSelectedTags
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.dialogs.BaseDialogContainer
import week.on.a.plate.core.uitools.dialogs.BottomDialogContainer
import week.on.a.plate.dialogs.changePortions.view.ChangePortionsPanel
import week.on.a.plate.dialogs.chooseHowImagePick.event.ChooseHowImagePickEvent
import week.on.a.plate.dialogs.chooseHowImagePick.logic.ChooseHowImagePickViewModel
import week.on.a.plate.dialogs.chooseHowImagePick.view.ChooseHowImagePickContent
import week.on.a.plate.dialogs.chooseIngredientsForStep.event.ChooseIngredientsForStepEvent
import week.on.a.plate.dialogs.chooseIngredientsForStep.logic.ChooseIngredientsForStepViewModel
import week.on.a.plate.dialogs.chooseIngredientsForStep.view.ChooseIngredientsForStep
import week.on.a.plate.dialogs.cookStepMore.event.CookStepMoreEvent
import week.on.a.plate.dialogs.cookStepMore.logic.CookStepMoreDialogViewModel
import week.on.a.plate.dialogs.cookStepMore.view.CookStepMoreContent
import week.on.a.plate.dialogs.editOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.dialogs.editOrDelete.logic.EditOrDeleteViewModel
import week.on.a.plate.dialogs.editOrDelete.view.EditOrDeleteDialogContent
import week.on.a.plate.dialogs.editSelection.event.EditSelectionEvent
import week.on.a.plate.dialogs.editSelection.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.editSelection.view.EditSelectionContent
import week.on.a.plate.dialogs.dialogTimePick.event.TimePickEvent
import week.on.a.plate.dialogs.dialogTimePick.logic.TimePickViewModel
import week.on.a.plate.dialogs.dialogTimePick.view.TimePickDialog
import week.on.a.plate.dialogs.exitApply.event.ExitApplyEvent
import week.on.a.plate.dialogs.exitApply.logic.ExitApplyViewModel
import week.on.a.plate.dialogs.exitApply.view.ExitApplyContent
import week.on.a.plate.dialogs.filtersMore.event.FiltersMoreEvent
import week.on.a.plate.dialogs.filtersMore.logic.FiltersMoreViewModel
import week.on.a.plate.dialogs.filtersMore.view.FilterMoreContent
import week.on.a.plate.dialogs.setPermanentMeals.event.SetPermanentMealsEvent
import week.on.a.plate.dialogs.setPermanentMeals.logic.SetPermanentMealsViewModel
import week.on.a.plate.dialogs.setPermanentMeals.view.SetPermanentMealsStart
import week.on.a.plate.dialogs.setTheme.event.SetThemeEvent
import week.on.a.plate.dialogs.setTheme.logic.SetThemesViewModel
import week.on.a.plate.dialogs.setTheme.view.SetThemeStart
import week.on.a.plate.dialogs.sortMore.event.SortMoreEvent
import week.on.a.plate.dialogs.sortMore.logic.SortMoreViewModel
import week.on.a.plate.dialogs.sortMore.view.SortMoreContent
import week.on.a.plate.dialogs.calendarMy.view.CalendarMy


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsContainer(
    data: DialogViewModel<*>?,
    onEvent: (event: Event) -> Unit
) {
    when (data) {
        is ChooseWeekViewModel -> {
            val datePickerState = rememberDatePickerState()
            val state = ChooseWeekUIState(datePickerState)
            data.state = state
            BaseDialogContainer(data.show, { onEvent(ChooseWeekDialogEvent.Close) }) {
                Box(
                    Modifier
                        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
                        .padding(vertical = 36.dp)
                ) {
                    CalendarMy(data.stateCalendar, { event -> data.onEvent(event) }) {
                        onEvent(ChooseWeekDialogEvent.Done(it))
                    }
                }
            }
        }

        is EditRecipePositionViewModel -> {
            BaseDialogContainer(data.show, { onEvent(ActionMoreRecipePositionEvent.Close) }) {
                EditRecipePositionDialogContent() { event: ActionMoreRecipePositionEvent ->
                    onEvent(event)
                }
            }
        }

        is EditOtherPositionViewModel -> {
            BaseDialogContainer(data.show, { onEvent(OtherPositionMoreEvent.Close) }) {
                EditOtherPositionDialogContent(data.isForIngredient) { event: OtherPositionMoreEvent ->
                    onEvent(event)
                }
            }
        }

        is EditOrDeleteViewModel -> {
            BaseDialogContainer(data.show, { onEvent(EditOrDeleteEvent.Close) }) {
                EditOrDeleteDialogContent { event: EditOrDeleteEvent ->
                    onEvent(event)
                }
            }
        }

        is AddPositionViewModel -> {
            BaseDialogContainer(data.show, { onEvent(AddPositionEvent.Close) }) {
                AddPositionDialogContent() { event: AddPositionEvent ->
                    onEvent(event)
                }
            }
        }

        is EditOneStringViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(EditOneStringEvent.Close) }) {
                EditOneStringContent(data.state) {
                    onEvent(EditOneStringEvent.Done)
                }
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is ChangePortionsCountViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(ChangePortionsCountEvent.Close) }) {
                ChangePortionsPanel(data.state.portionsCount) { onEvent(ChangePortionsCountEvent.Done) }
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is EditPositionIngredientViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(EditPositionIngredientEvent.Close) }) {
                EditOrAddIngredientBottomDialogContent(
                    data.state
                ) { event: EditPositionIngredientEvent -> onEvent(event) }
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is DatePickerViewModel -> {
            val datePickerState = rememberDatePickerState()
            val state = DatePickerUIState(datePickerState)
            data.state = state
            DatePickerMy(
                state = data.state.state,
                showState = data.show,
                onClose = { onEvent(DatePickerEvent.Close) }) {
                onEvent(DatePickerEvent.Done)
            }
        }

        is AddTagViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(AddTagEvent.Close) }) {
                AddTag(
                    state = data.state
                ) { addTagEvent: AddTagEvent -> onEvent(addTagEvent) }
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is AddIngredientViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(AddIngredientEvent.Close) }) {
                AddIngredient(
                    state = data.state
                ) { addIngredientEvent: AddIngredientEvent -> onEvent(addIngredientEvent) }
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is SelectedFiltersViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(SelectedFiltersEvent.Close) }) {
                DialogSelectedTags(
                    data.state
                ) { event: SelectedFiltersEvent -> onEvent(event) }
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is FilterVoiceApplyViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(FilterVoiceApplyEvent.Close) }) {
                DialogVoiceApplyTags(
                    data.state
                ) { event: FilterVoiceApplyEvent -> onEvent(event) }
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is TimePickViewModel -> {
            TimePickDialog(data.state) { event: TimePickEvent ->
                data.onEvent(event)
            }
        }

        is EditSelectionViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(EditSelectionEvent.Close) }) {
                EditSelectionContent(
                    data
                )
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is SortMoreViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(SortMoreEvent.Close) }) {
                SortMoreContent(data::onEvent)
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is FiltersMoreViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(FiltersMoreEvent.Close) }) {
                FilterMoreContent(data.state)
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is CookStepMoreDialogViewModel -> {
            BaseDialogContainer(data.show, { onEvent(CookStepMoreEvent.Close) }) {
                CookStepMoreContent() { event: CookStepMoreEvent ->
                    onEvent(event)
                }
            }
        }

        is ChooseIngredientsForStepViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { data.onEvent(ChooseIngredientsForStepEvent.Close) }) {
                val state = data.state
                val event1 = { event: ChooseIngredientsForStepEvent ->
                    data.onEvent(event)
                }
                ChooseIngredientsForStep(state, event1 )
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is ExitApplyViewModel -> {
            BaseDialogContainer(data.show, { data.onEvent(ExitApplyEvent.Close) }) {
                ExitApplyContent () { event: ExitApplyEvent ->
                    data.onEvent(event)
                }
            }
        }

        is ChooseHowImagePickViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { data.onEvent(ChooseHowImagePickEvent.Close) }) {
                ChooseHowImagePickContent(){ event: ChooseHowImagePickEvent ->
                    data.onEvent(event)
                }
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is SetThemesViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(SetThemeEvent.Close) }) {
                SetThemeStart(
                    data.state
                ) { event: SetThemeEvent -> onEvent(event) }
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is SetPermanentMealsViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(SetPermanentMealsEvent.Close) }) {
                SetPermanentMealsStart(
                    data.state
                ) { event: SetPermanentMealsEvent -> onEvent(event) }
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        null -> {}
    }
}
