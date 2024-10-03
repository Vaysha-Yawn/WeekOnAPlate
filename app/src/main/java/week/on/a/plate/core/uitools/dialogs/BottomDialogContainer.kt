package week.on.a.plate.core.uitools.dialogs

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomDialogContainer(
    state: SheetState,
    onClose: () -> Unit,
    content: @Composable (snackBar:SnackbarHostState) -> Unit
) {
    if (state.isVisible) {
        ModalBottomSheet(
            onDismissRequest = { onClose() },
            sheetState = state,
            containerColor = MaterialTheme.colorScheme.surface,
            contentWindowInsets = {
                WindowInsets.ime
            }
        ) {
            val snackbarHostState = SnackbarHostState()
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState) {
                        if (snackbarHostState.currentSnackbarData != null) {
                            Snackbar(
                                snackbarData = snackbarHostState.currentSnackbarData!!,
                                modifier = Modifier.padding(bottom = 80.dp),
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            ) { innerPadding ->
                content(snackbarHostState)
                innerPadding
            }
        }
    }
}