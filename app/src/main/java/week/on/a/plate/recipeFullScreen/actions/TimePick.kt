package week.on.a.plate.recipeFullScreen.actions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import week.on.a.plate.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onCancel: () -> Unit,
    onConfirm: (Calendar) -> Unit,
) {

    val time = Calendar.getInstance()
    time.timeInMillis = System.currentTimeMillis()

    val mode = remember { mutableStateOf(DisplayMode.Picker) }
    val timeState: TimePickerState = rememberTimePickerState(
        initialHour = time[Calendar.HOUR_OF_DAY],
        initialMinute = time[Calendar.MINUTE],
        true
    )

    fun onConfirmClicked() {
        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, timeState.hour)
        cal.set(Calendar.MINUTE, timeState.minute)
        cal.isLenient = false

        onConfirm(cal)
    }

    PickerDialog(
        onDismissRequest = onCancel,
        buttons = {
            DisplayModeToggleButton(
                displayMode = mode.value,
                onDisplayModeChange = { mode.value = it },
            )
            Spacer(Modifier.weight(1f))
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
            TextButton(onClick = ::onConfirmClicked) {
                Text("Confirm")
            }
        },
    ) {
        val contentModifier = Modifier.padding(horizontal = 24.dp)
        when (mode.value) {
            DisplayMode.Picker -> TimePicker(modifier = contentModifier, state = timeState)
            DisplayMode.Input -> TimeInput(modifier = contentModifier, state = timeState)
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
