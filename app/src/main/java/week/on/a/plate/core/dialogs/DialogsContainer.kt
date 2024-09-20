package week.on.a.plate.core.dialogs

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import week.on.a.plate.core.dialogs.menu.addPosition.event.AddPositionEvent
import week.on.a.plate.core.dialogs.menu.addPosition.logic.AddPositionViewModel
import week.on.a.plate.core.dialogs.menu.addPosition.view.AddPositionDialogContent
import week.on.a.plate.core.dialogs.menu.changePositionCount.event.ChangePortionsCountEvent
import week.on.a.plate.core.dialogs.menu.changePositionCount.logic.ChangePortionsCountViewModel
import week.on.a.plate.core.dialogs.menu.changePositionCount.state.ChangePortionsCountUIState
import week.on.a.plate.core.dialogs.menu.changePositionCount.view.ChangePortionsPanel
import week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.state.ChooseWeekUIState
import week.on.a.plate.core.dialogs.menu.chooseWeekInMenu.view.ChooseDate
import week.on.a.plate.core.dialogs.menu.editNote.event.EditNoteEvent
import week.on.a.plate.core.dialogs.menu.editNote.logic.EditNoteViewModel
import week.on.a.plate.core.dialogs.menu.editNote.state.EditNoteUIState
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
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.uitools.dialogs.BaseDialogContainer
import week.on.a.plate.core.uitools.dialogs.BottomDialogContainer


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsContainer(data: DialogViewModel?, onEvent: (event: Event) -> Unit) {

    when (data) {
        is ChooseWeekViewModel -> {
            val datePickerState = rememberDatePickerState()
            val state = ChooseWeekUIState(datePickerState)
            data.state = state

            ChooseDate(state, {
                onEvent(ChooseWeekDialogEvent.Close)
            }, {
                onEvent(ChooseWeekDialogEvent.Done)
            })
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
            data.state = EditNoteUIState(sheetState, data.position)
            BottomDialogContainer(data.state.sheetState, { onEvent(EditNoteEvent.Close) }) {
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
            data.state = ChangePortionsCountUIState(sheetState, data.startValue)
            BottomDialogContainer(
                data.state.sheetState,
                { onEvent(ChangePortionsCountEvent.Close) }) {
                ChangePortionsPanel(data.state.portionsCount) { onEvent(ChangePortionsCountEvent.Done) }
            }
            LaunchedEffect(true) {
                sheetState.show()
            }
        }

        is EditPositionIngredientViewModel -> {
            val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
            data.state = EditPositionIngredientUIState(data.oldPositionIngredient, sheetState)
            BottomDialogContainer(data.state.sheetState, { onEvent(EditPositionIngredientEvent.Close) }) {
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

        null -> {}
        /*
        is DialogType.RegisterToShopList -> {
            //
        }
        is DialogType.CreateCategoryIngredient -> TODO()
        is DialogType.CreateIngredient -> TODO()
        is DialogType.FindIngredient -> TODO()
        is DialogType.FindCategoryIngredient -> TODO()
        is DialogType.FindIngredients -> TODO()
        is DialogType.SelectedFilters -> TODO()
        is DialogType.CreateTag -> TODO()
        DialogType.DeleteAsk -> TODO()
        DialogType.EditRecipePosition -> TODO()
        DialogType.FindTags -> TODO()
        DialogType.RecipeToShopList -> TODO()
        DialogType.SelectedToShopList -> TODO()
        DialogType.SpecifyDate -> TODO()
        DialogType.VoiceFiltersApply -> TODO()
        null -> {}*/
    }
}