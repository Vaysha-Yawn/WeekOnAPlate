package week.on.a.plate.core.uitools.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Composable
fun BaseDialogContainer(
    showState: MutableState<Boolean>,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    if (showState.value) {
        Dialog(onDismissRequest = { onClose() }) {
            Column(Modifier.background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))) {
                content()
            }
        }
    }
}