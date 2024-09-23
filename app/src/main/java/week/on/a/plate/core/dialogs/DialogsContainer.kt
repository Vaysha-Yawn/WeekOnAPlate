package week.on.a.plate.core.dialogs

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import week.on.a.plate.core.dialogs.addCategory.event.AddCategoryEvent
import week.on.a.plate.core.dialogs.addCategory.logic.AddCategoryViewModel
import week.on.a.plate.core.dialogs.addCategory.state.AddCategoryUIState
import week.on.a.plate.core.dialogs.addCategory.view.AddCategory
import week.on.a.plate.core.dialogs.addIngrdient.event.AddIngredientEvent
import week.on.a.plate.core.dialogs.addIngrdient.logic.AddIngredientViewModel
import week.on.a.plate.core.dialogs.addIngrdient.view.AddIngredient
import week.on.a.plate.core.dialogs.addTag.event.AddTagEvent
import week.on.a.plate.core.dialogs.addTag.logic.AddTagViewModel
import week.on.a.plate.core.dialogs.addTag.view.AddTag
import week.on.a.plate.core.dialogs.filter.filterVoiceApply.event.FilterVoiceApplyEvent
import week.on.a.plate.core.dialogs.filter.filterVoiceApply.logic.FilterVoiceApplyViewModel
import week.on.a.plate.core.dialogs.filter.filterVoiceApply.view.DialogVoiceApplyTags
import week.on.a.plate.core.dialogs.menu.addPosition.event.AddPositionEvent
import week.on.a.plate.core.dialogs.menu.addPosition.logic.AddPositionViewModel
import week.on.a.plate.core.dialogs.menu.addPosition.view.AddPositionDialogContent
import week.on.a.plate.core.dialogs.menu.changePositionCount.event.ChangePortionsCountEvent
import week.on.a.plate.core.dialogs.menu.changePositionCount.logic.ChangePortionsCountViewModel
import week.on.a.plate.core.dialogs.menu.changePositionCount.view.ChangePortionsPanel
import week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.state.ChooseWeekUIState
import week.on.a.plate.core.dialogs.menu.datePicker.event.DatePickerEvent
import week.on.a.plate.core.dialogs.menu.datePicker.logic.DatePickerViewModel
import week.on.a.plate.core.dialogs.menu.datePicker.state.DatePickerUIState
import week.on.a.plate.core.dialogs.menu.datePicker.view.DatePickerMy
import week.on.a.plate.core.dialogs.menu.editNote.event.EditNoteEvent
import week.on.a.plate.core.dialogs.menu.editNote.logic.EditNoteViewModel
import week.on.a.plate.core.dialogs.menu.editNote.view.EditNoteBottomDialogContent
import week.on.a.plate.core.dialogs.menu.editOtherPosition.event.EditOtherPositionEvent
import week.on.a.plate.core.dialogs.menu.editOtherPosition.logic.EditOtherPositionViewModel
import week.on.a.plate.core.dialogs.menu.editOtherPosition.view.EditOtherPositionDialogContent
import week.on.a.plate.core.dialogs.menu.editPositionIngredient.event.EditPositionIngredientEvent
import week.on.a.plate.core.dialogs.menu.editPositionIngredient.logic.EditPositionIngredientViewModel
import week.on.a.plate.core.dialogs.menu.editPositionIngredient.state.EditPositionIngredientUIState
import week.on.a.plate.core.dialogs.menu.editPositionIngredient.view.EditOrAddIngredientBottomDialogContent
import week.on.a.plate.core.dialogs.menu.editRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.core.dialogs.menu.editRecipePosition.logic.EditRecipePositionViewModel
import week.on.a.plate.core.dialogs.menu.editRecipePosition.view.EditRecipePositionDialogContent
import week.on.a.plate.core.dialogs.filter.selectedFilters.event.SelectedFiltersEvent
import week.on.a.plate.core.dialogs.filter.selectedFilters.logic.SelectedFiltersViewModel
import week.on.a.plate.core.dialogs.filter.selectedFilters.view.DialogSelectedTags
import week.on.a.plate.core.Event
import week.on.a.plate.core.MainEvent
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
