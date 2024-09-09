package week.on.a.plate.menuScreen.view.dialogs.bottomSheets

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomDialogContainer(
    state: SheetState,
    content: @Composable ()->Unit
) {
    if (state.isVisible){
        val scope = rememberCoroutineScope()
        ModalBottomSheet(
            onDismissRequest = { scope.launch { state.hide() } },
            sheetState = state,
            containerColor = MaterialTheme.colorScheme.background,
        ) { content() }
    }
}