package week.on.a.plate.menuScreen.view.dialogs

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.uitools.dialogs.BaseDialogContainer
import week.on.a.plate.core.uitools.dialogs.BottomDialogContainer
import week.on.a.plate.fullScreenDialogs.view.DatePickerMy
import week.on.a.plate.core.tools.dateToLocalDate
import week.on.a.plate.menuScreen.data.eventData.DialogData
import week.on.a.plate.menuScreen.data.eventData.MenuEvent
import week.on.a.plate.menuScreen.data.stateData.MenuIUState
import week.on.a.plate.menuScreen.view.dialogs.bottomSheets.AddIngredientBottomDialogContent
import week.on.a.plate.menuScreen.view.dialogs.bottomSheets.AddNoteBottomDialogContent
import week.on.a.plate.menuScreen.view.dialogs.bottomSheets.ChangePortionsPanelDialogWrapper
import week.on.a.plate.menuScreen.view.dialogs.bottomSheets.EditIngredientBottomDialogContent
import week.on.a.plate.menuScreen.view.dialogs.bottomSheets.EditNoteBottomDialogContent
import week.on.a.plate.menuScreen.view.dialogs.editDialogs.AddPositionDialogContent
import week.on.a.plate.menuScreen.view.dialogs.editDialogs.EditOtherPositionDialogContent
import week.on.a.plate.menuScreen.view.dialogs.editDialogs.EditRecipePositionDialogContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsContainer(uiState: MenuIUState, onEvent: (event: MenuEvent) -> Unit) {
    val data = uiState.dialogState.value

    when (data) {
        is DialogData.AddIngredient -> {
            BottomDialogContainer(data.sheetState, { onEvent(MenuEvent.CloseDialog) }) {
                AddIngredientBottomDialogContent(data, onEvent)
            }
        }

        is DialogData.AddNote -> {
            BottomDialogContainer(data.sheetState, { onEvent(MenuEvent.CloseDialog) }) {
                AddNoteBottomDialogContent(data)
            }
        }

        is DialogData.EditIngredient -> {
            BottomDialogContainer(data.sheetState, { onEvent(MenuEvent.CloseDialog) }) {
                EditIngredientBottomDialogContent(data, onEvent)
            }
        }

        is DialogData.EditNote -> {
            BottomDialogContainer(data.sheetState, { onEvent(MenuEvent.CloseDialog) }) {
                EditNoteBottomDialogContent(data, onEvent)
            }
        }

        is DialogData.AddPosition -> {
            BaseDialogContainer(data.show, { onEvent(MenuEvent.CloseDialog) }) {
                AddPositionDialogContent(data.selectionId, onEvent)
            }
        }

        is DialogData.AddPositionNeedSelId -> {
            BaseDialogContainer(data.show, { onEvent(MenuEvent.CloseDialog) }) {
                AddPositionDialogContent(null, onEvent)
            }
        }

        is DialogData.ChangePortionsCount -> {
            BottomDialogContainer(data.sheetState, { onEvent(MenuEvent.CloseDialog) }) {
                ChangePortionsPanelDialogWrapper(data)
            }
        }


        is DialogData.EditPosition -> {
            BaseDialogContainer(data.show, { onEvent(MenuEvent.CloseDialog) }) {
                if (data.position is Position.PositionRecipeView) {
                    EditRecipePositionDialogContent(data.position, onEvent)
                } else {
                    EditOtherPositionDialogContent(data.position, onEvent)
                }
            }
        }

        is DialogData.SelectedToShopList -> {
            //
        }

        is DialogData.ToShopList -> {
            //
        }

        is DialogData.ChooseDay -> {
            DatePickerMy(
                data.state,
                data.show,
                {
                    onEvent(MenuEvent.CloseDialog)
                    data.show.value = false
                }) {
                val date = data.state.selectedDateMillis
                if (date != null) {
                    onEvent(MenuEvent.ChangeWeek(date.dateToLocalDate()))
                }
                onEvent(MenuEvent.CloseDialog)
                data.show.value = false
            }
        }


        is DialogData.CreateCategoryIngredient -> TODO()
        is DialogData.CreateIngredient -> TODO()
        is DialogData.FindIngredient -> TODO()
        is DialogData.SelectCategoryIngredient -> TODO()
        is DialogData.SelectIngredients -> TODO()

        null -> {}
    }
}