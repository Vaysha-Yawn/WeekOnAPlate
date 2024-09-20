package week.on.a.plate.core.dialogs.view.base

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.chooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.core.dialogs.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.core.dialogs.chooseWeekInMenu.state.ChooseWeekUIState
import week.on.a.plate.core.dialogs.chooseWeekInMenu.view.ChooseDate
import week.on.a.plate.core.dialogs.editRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.core.dialogs.editRecipePosition.logic.EditRecipePositionViewModel
import week.on.a.plate.core.dialogs.editRecipePosition.state.EditRecipePositionUIState
import week.on.a.plate.core.dialogs.editRecipePosition.view.EditRecipePositionDialogContent
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.uitools.dialogs.BaseDialogContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsContainer(data: DialogViewModel?, onEvent: (event: Event) -> Unit) {
  /*  val sheetState = rememberModalBottomSheetState()
    LaunchedEffect(key1 = data) {
        if (data != null) {
            sheetState.show()
        }
    }*/

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

        null -> {}
        /* is DialogType.CreateIngredientPosition -> {
             BottomDialogContainer(sheetState, { onEvent(MainEvent.CloseDialog) }) {
                // AddIngredientBottomDialogContent(data, onEvent)
             }
         }

         is DialogType.CreateNote -> {
             BottomDialogContainer(sheetState, { onEvent(MainEvent.CloseDialog) }) {
                // AddNoteBottomDialogContent(data)
             }
         }

         is DialogType.EditIngredientPosition -> {
             BottomDialogContainer(sheetState, { onEvent(MainEvent.CloseDialog) }) {
                // EditIngredientBottomDialogContent(data, onEvent)
             }
         }

         is DialogType.EditNote -> {
             BottomDialogContainer(sheetState, { onEvent(MainEvent.CloseDialog) }) {
                // EditNoteBottomDialogContent(data)
             }
         }

         is DialogType.CreatePosition -> {
             *//*BaseDialogContainer(show, { onEvent(MainEvent.CloseDialog) }) {
                AddPositionDialogContent(data.selectionId, onEvent)
            }*//*
        }

        is DialogType.ChangePortionsCount -> {
            BottomDialogContainer(sheetState, { onEvent(MainEvent.CloseDialog) }) {
              //  ChangePortionsPanelDialogWrapper(data)
            }
        }


        is DialogType.EditPosition -> {
            *//*BaseDialogContainer(show, { onEvent(MainEvent.CloseDialog) }) {
                if (data.position is Position.PositionRecipeView) {
                    EditRecipePositionDialogContent(data.position, onEvent)
                } else {
                    EditOtherPositionDialogContent(data.position, onEvent)
                }
            }*//*
        }

        is DialogType.RegisterToShopList -> {
            //
        }

        is DialogType.ChooseWeek -> {
            val stattt = rememberDatePickerState()
            val state = ChooseWeekUIState(stattt)
            val vm = hiltViewModel<ChooseWeekViewModel>()
            vm.mainViewModel = mainViewModel
            vm.state = state
            ChooseDate(state, {vm.close()}, {vm.done()})
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