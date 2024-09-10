package week.on.a.plate.menuScreen.view.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog


@Composable
fun BaseDialogContainer(
    showState: MutableState<Boolean>,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    if (showState.value) {
        Dialog(onDismissRequest = { onClose() }) {
            Column(Modifier.background(MaterialTheme.colorScheme.background)) {
                content()
            }
        }
    }
}