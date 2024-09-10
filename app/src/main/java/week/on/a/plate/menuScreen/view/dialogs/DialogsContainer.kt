package week.on.a.plate.menuScreen.view.dialogs

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.menuScreen.logic.eventData.DialogMenuData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.menuScreen.logic.eventData.MenuIUState
import week.on.a.plate.menuScreen.view.dialogs.bottomSheets.AddIngredientBottomDialogContent
import week.on.a.plate.menuScreen.view.dialogs.bottomSheets.AddNoteBottomDialogContent
import week.on.a.plate.menuScreen.view.dialogs.bottomSheets.BottomDialogContainer
import week.on.a.plate.menuScreen.view.dialogs.bottomSheets.ChangePortionsPanel
import week.on.a.plate.menuScreen.view.dialogs.bottomSheets.EditIngredientBottomDialogContent
import week.on.a.plate.menuScreen.view.dialogs.bottomSheets.EditNoteBottomDialogContent
import week.on.a.plate.menuScreen.view.dialogs.dialogFullScreen.AddRecipeDialogContent
import week.on.a.plate.menuScreen.view.dialogs.dialogFullScreen.DoublePositionDialogContent
import week.on.a.plate.menuScreen.view.dialogs.dialogFullScreen.MovePositionDialogContent
import week.on.a.plate.menuScreen.view.dialogs.editDialogs.AddPositionDialogContent
import week.on.a.plate.menuScreen.view.dialogs.editDialogs.EditOtherPositionDialogContent
import week.on.a.plate.menuScreen.view.dialogs.editDialogs.EditRecipePositionDialogContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogsContainer(uiState: MenuIUState, onEvent: (event: MenuEvent) -> Unit) {
    val data = uiState.dialogState.value
    when (data) {
        is DialogMenuData.AddIngredient -> {
            BottomDialogContainer(data.sheetState, { onEvent(MenuEvent.CloseDialog) }) {
                AddIngredientBottomDialogContent(data, onEvent)
            }
        }

        is DialogMenuData.AddNote -> {
            BottomDialogContainer(data.sheetState, { onEvent(MenuEvent.CloseDialog) }) {
                AddNoteBottomDialogContent(data, onEvent)
            }
        }

        is DialogMenuData.EditIngredient -> {
            BottomDialogContainer(data.sheetState, { onEvent(MenuEvent.CloseDialog) }) {
                EditIngredientBottomDialogContent(data, onEvent)
            }
        }

        is DialogMenuData.EditNote -> {
            BottomDialogContainer(data.sheetState, { onEvent(MenuEvent.CloseDialog) }) {
                EditNoteBottomDialogContent(data, onEvent)
            }
        }

        is DialogMenuData.AddPosition -> {
            BaseDialogContainer(data.show, { onEvent(MenuEvent.CloseDialog) }) {
                AddPositionDialogContent(date = data.date, category = data.category, onEvent)
            }
        }

        is DialogMenuData.ChangePortionsCount -> {
            BottomDialogContainer(data.sheetState, { onEvent(MenuEvent.CloseDialog) }) {
                ChangePortionsPanel(data, onEvent)
            }
        }

        is DialogMenuData.AddPositionToMenu -> {
            BaseDialogContainer(data.show, { onEvent(MenuEvent.CloseDialog) }) {
                AddRecipeDialogContent(data = data, onEvent)
            }
        }

        is DialogMenuData.DoublePositionToMenu -> {
            BaseDialogContainer(data.show, { onEvent(MenuEvent.CloseDialog) }) {
                DoublePositionDialogContent(data = data, onEvent)
            }
        }

        is DialogMenuData.MovePositionToMenu -> {
            BaseDialogContainer(data.show, { onEvent(MenuEvent.CloseDialog) }) {
                MovePositionDialogContent(data = data, onEvent)
            }
        }

        is DialogMenuData.EditPosition -> {
            BaseDialogContainer(data.show, { onEvent(MenuEvent.CloseDialog) }) {
                if (data.position is Position.PositionRecipeView) {
                    EditRecipePositionDialogContent(data.position, onEvent)
                } else {
                    EditOtherPositionDialogContent(data.position, onEvent)
                }
            }
        }

        is DialogMenuData.SelectedToShopList -> {
            //
        }

        null -> {}
    }
}