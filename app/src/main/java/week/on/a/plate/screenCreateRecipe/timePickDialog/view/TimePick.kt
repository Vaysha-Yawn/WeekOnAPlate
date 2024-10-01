package week.on.a.plate.screenCreateRecipe.timePickDialog.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import week.on.a.plate.R
import week.on.a.plate.screenCreateRecipe.timePickDialog.event.TimePickEvent
import week.on.a.plate.screenCreateRecipe.timePickDialog.state.TimePickUIState
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickDialog(
    state: TimePickUIState,
    onEvent: (TimePickEvent)->Unit
) {
    PickerDialog(
        onDismissRequest = { onEvent(TimePickEvent.Close) },
        buttons = {
            DisplayModeToggleButton(
                displayMode = state.mode.value,
                onDisplayModeChange = { state.mode.value = it },
            )
            Spacer(Modifier.weight(1f))
            TextButton(onClick = { onEvent(TimePickEvent.Close) }) {
                Text("Отмена")
            }
            TextButton(onClick = { onEvent(TimePickEvent.Done) }) {
                Text("Подтвердить")
            }
        },
    ) {
        val contentModifier = Modifier.padding(horizontal = 24.dp)
        when (state.mode.value) {
            DisplayMode.Picker -> TimePicker(modifier = contentModifier, state = state.timeState)
            DisplayMode.Input -> TimeInput(modifier = contentModifier, state = state.timeState)
        }
    }
}


@Composable
fun PickerDialog(
    onDismissRequest: () -> Unit,
    buttons: @Composable RowScope.() -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier,
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(24.dp))
                content()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, end = 24.dp, start = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                ) {
                    buttons()
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DisplayModeToggleButton(
    displayMode: DisplayMode,
    onDisplayModeChange: (DisplayMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (displayMode) {
        DisplayMode.Picker -> IconButton(
            modifier = modifier,
            onClick = { onDisplayModeChange(DisplayMode.Input) },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.keyboard),
                contentDescription = "",
            )
        }

        DisplayMode.Input -> IconButton(
            modifier = modifier,
            onClick = { onDisplayModeChange(DisplayMode.Picker) },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.time),
                contentDescription = "",
            )
        }
    }
}