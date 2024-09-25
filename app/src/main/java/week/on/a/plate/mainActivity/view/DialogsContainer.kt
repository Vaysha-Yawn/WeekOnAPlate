package week.on.a.plate.mainActivity.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import week.on.a.plate.dialogsAddFilters.addCategory.event.AddCategoryEvent
import week.on.a.plate.dialogsAddFilters.addCategory.logic.AddCategoryViewModel
import week.on.a.plate.dialogsAddFilters.addCategory.view.AddCategory
import week.on.a.plate.dialogsAddFilters.addIngrdient.event.AddIngredientEvent
import week.on.a.plate.dialogsAddFilters.addIngrdient.logic.AddIngredientViewModel
import week.on.a.plate.dialogsAddFilters.addIngrdient.view.AddIngredient
import week.on.a.plate.dialogsAddFilters.addTag.event.AddTagEvent
import week.on.a.plate.dialogsAddFilters.addTag.logic.AddTagViewModel
import week.on.a.plate.dialogsAddFilters.addTag.view.AddTag
import week.on.a.plate.screenFilters.dialogs.filterVoiceApply.event.FilterVoiceApplyEvent
import week.on.a.plate.screenFilters.dialogs.filterVoiceApply.logic.FilterVoiceApplyViewModel
import week.on.a.plate.screenFilters.dialogs.filterVoiceApply.view.DialogVoiceApplyTags
import week.on.a.plate.screenMenu.dialogs.addPosition.event.AddPositionEvent
import week.on.a.plate.screenMenu.dialogs.addPosition.logic.AddPositionViewModel
import week.on.a.plate.screenMenu.dialogs.addPosition.view.AddPositionDialogContent
import week.on.a.plate.screenMenu.dialogs.changePositionCount.event.ChangePortionsCountEvent
import week.on.a.plate.screenMenu.dialogs.changePositionCount.logic.ChangePortionsCountViewModel
import week.on.a.plate.screenMenu.dialogs.changePositionCount.view.ChangePortionsPanel
import week.on.a.plate.screenMenu.dialogs.chooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.screenMenu.dialogs.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.screenMenu.dialogs.chooseWeekInMenu.state.ChooseWeekUIState
import week.on.a.plate.screenMenu.dialogs.datePicker.event.DatePickerEvent
import week.on.a.plate.screenMenu.dialogs.datePicker.logic.DatePickerViewModel
import week.on.a.plate.screenMenu.dialogs.datePicker.state.DatePickerUIState
import week.on.a.plate.screenMenu.dialogs.datePicker.view.DatePickerMy
import week.on.a.plate.screenMenu.dialogs.editNote.event.EditNoteEvent
import week.on.a.plate.screenMenu.dialogs.editNote.logic.EditNoteViewModel
import week.on.a.plate.screenMenu.dialogs.editNote.view.EditNoteBottomDialogContent
import week.on.a.plate.screenMenu.dialogs.editOtherPosition.event.EditOtherPositionEvent
import week.on.a.plate.screenMenu.dialogs.editOtherPosition.logic.EditOtherPositionViewModel
import week.on.a.plate.screenMenu.dialogs.editOtherPosition.view.EditOtherPositionDialogContent
import week.on.a.plate.screenMenu.dialogs.editPositionIngredient.event.EditPositionIngredientEvent
import week.on.a.plate.screenMenu.dialogs.editPositionIngredient.logic.EditPositionIngredientViewModel
import week.on.a.plate.screenMenu.dialogs.editPositionIngredient.view.EditOrAddIngredientBottomDialogContent
import week.on.a.plate.screenMenu.dialogs.editRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.screenMenu.dialogs.editRecipePosition.logic.EditRecipePositionViewModel
import week.on.a.plate.screenMenu.dialogs.editRecipePosition.view.EditRecipePositionDialogContent
import week.on.a.plate.screenFilters.dialogs.selectedFilters.event.SelectedFiltersEvent
import week.on.a.plate.screenFilters.dialogs.selectedFilters.logic.SelectedFiltersViewModel
import week.on.a.plate.screenFilters.dialogs.selectedFilters.view.DialogSelectedTags
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.core.uitools.dialogs.BaseDialogContainer
import week.on.a.plate.core.uitools.dialogs.BottomDialogContainer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsContainer(data: DialogViewModel?, onEvent: (event: Event) -> Unit) {

    when (data) {
        is ChooseWeekViewModel -> {
            val datePickerState = rememberDatePickerState()
            val state = ChooseWeekUIState(datePickerState)
            data.state = state
            DatePickerMy(
                state = data.state.state,
                showState = data.state.show,
                onClose = { onEvent(ChooseWeekDialogEvent.Close) }) {
                onEvent(ChooseWeekDialogEvent.Done)
            }
        }

        is EditRecipePositionViewModel -> {
            BaseDialogContainer(data.state.show, { onEvent(EditRecipePositionEvent.Close) }) {
                EditRecipePositionDialogContent() { event: EditRecipePositionEvent ->
                    onEvent(event)
                }
            }
        }

        is EditOtherPositionViewModel -> {
            BaseDialogContainer(data.state.show, { onEvent(EditOtherPositionEvent.Close) }) {
                EditOtherPositionDialogContent { event: EditOtherPositionEvent ->
                    onEvent(event)
                }
            }
        }

        is AddPositionViewModel -> {
            BaseDialogContainer(data.state.show, { onEvent(AddPositionEvent.Close) }) {
                AddPositionDialogContent() { event: AddPositionEvent ->
                    onEvent(event)
                }
            }
        }

        is EditNoteViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(sheetState, { onEvent(EditNoteEvent.Close) }) {
                EditNoteBottomDialogContent(data.state.text) {
                    onEvent(EditNoteEvent.Done)
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
                EditOrAddIngredientBottomDialogContent(data.state,
                    { event: EditPositionIngredientEvent -> onEvent(event) }) { event: MainEvent ->
                    onEvent(
                        event
                    )
                }
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
                showState = data.state.show,
                onClose = { onEvent(DatePickerEvent.Close) }) {
                onEvent(DatePickerEvent.Done)
            }
        }

        is AddCategoryViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(AddCategoryEvent.Close) }) {
                AddCategory(
                    data.state,
                    { mainEvent: MainEvent -> onEvent(mainEvent) },
                    { onEvent(AddCategoryEvent.Done) },
                )
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is AddTagViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            BottomDialogContainer(
                sheetState,
                { onEvent(AddTagEvent.Close) }) {
                AddTag(
                    state = data.state,
                    { mainEvent: MainEvent -> onEvent(mainEvent) },
                    { addTagEvent: AddTagEvent -> onEvent(addTagEvent) })
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
                    state = data.state,
                    { mainEvent: MainEvent -> onEvent(mainEvent) },
                    { addIngredientEvent: AddIngredientEvent -> onEvent(addIngredientEvent) })
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

        null -> {}
    }
}
