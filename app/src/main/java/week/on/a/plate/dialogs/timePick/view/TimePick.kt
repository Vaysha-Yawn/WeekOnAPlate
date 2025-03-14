package week.on.a.plate.dialogs.timePick.view

import androidx.compose.foundation.background
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
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorSubTextGrey
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.dialogs.timePick.event.TimePickEvent
import week.on.a.plate.dialogs.timePick.state.TimePickUIState
import week.on.a.plate.core.uitools.clickNoRipple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickDialog(
    state: TimePickUIState,
    onEvent: (TimePickEvent)->Unit
) {
    PickerDialog(
        state,
        onDismissRequest = { onEvent(TimePickEvent.Close) },
        buttons = {
            DisplayModeToggleButton(
                displayMode = state.mode.value,
                onDisplayModeChange = { state.mode.value = it },
            )
            Spacer(Modifier.weight(1f))
            TextBody(
                stringResource(R.string.cancel),
                modifier = Modifier
                    .clickNoRipple { onEvent(TimePickEvent.Close) }
                    .padding(end = 24.dp),
                color = ColorSubTextGrey
            )
            TextBody(stringResource(R.string.apply), modifier = Modifier.clickNoRipple { onEvent(TimePickEvent.Done) })
        },
    ) {
        val contentModifier = Modifier.padding(horizontal = 24.dp)
        val colors = TimePickerDefaults.colors(
            clockDialColor = MaterialTheme.colorScheme.background,
            clockDialSelectedContentColor = MaterialTheme.colorScheme.onBackground,
            clockDialUnselectedContentColor = ColorSubTextGrey,
            selectorColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.background,
            timeSelectorSelectedContainerColor = MaterialTheme.colorScheme.background,
            timeSelectorUnselectedContainerColor = MaterialTheme.colorScheme.background,
            timeSelectorSelectedContentColor = MaterialTheme.colorScheme.onBackground,
            timeSelectorUnselectedContentColor = ColorSubTextGrey,
        )
        when (state.mode.value) {
            DisplayMode.Picker -> {
                TextBody(stringResource(R.string.select_hours_and_minutes))
                Spacer(Modifier.height(12.dp))
                TimePicker(modifier = contentModifier, state = state.timeState, colors = colors)
            }
            DisplayMode.Input -> TimeInput(modifier = contentModifier, state = state.timeState, colors = colors)
        }
    }
}


@Composable
fun PickerDialog(
    state: TimePickUIState,
    onDismissRequest: () -> Unit,
    buttons: @Composable() (RowScope.() -> Unit),
    content: @Composable() (ColumnScope.() -> Unit),
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier.background(MaterialTheme.colorScheme.surface),
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier =  Modifier.background(MaterialTheme.colorScheme.surface)) {
                Spacer(modifier = Modifier.height(12.dp))
                TextBody(stringResource(state.title) )
                Spacer(modifier = Modifier.height(12.dp))
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
                contentDescription = "Input time", tint = MaterialTheme.colorScheme.onBackground
            )
        }

        DisplayMode.Input -> IconButton(
            modifier = modifier,
            onClick = { onDisplayModeChange(DisplayMode.Picker) },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.time),
                contentDescription = "Pick time", tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}