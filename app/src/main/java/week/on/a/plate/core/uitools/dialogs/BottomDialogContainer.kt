package week.on.a.plate.core.uitools.dialogs

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomDialogContainer(
    state: SheetState,
    onClose:()->Unit,
    content: @Composable ()->Unit
) {
    if (state.isVisible){
        ModalBottomSheet(
            onDismissRequest = { onClose()},
            sheetState = state,
            containerColor = MaterialTheme.colorScheme.background,
        ) { content() }
    }
}