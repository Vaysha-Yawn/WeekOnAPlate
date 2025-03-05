package week.on.a.plate.dialogs.datePick.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.buttons.DoneButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerMy(
    state: DatePickerState,
    showState: MutableState<Boolean>,
    onClose: () -> Unit,
    done: () -> Unit
) {
    if (showState.value) {
        DatePickerDialog(
            onDismissRequest = {
                onClose()
            },
            confirmButton = {
                DoneButton(
                    text = stringResource(id = R.string.apply),
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 24.dp)
                ) {
                    done()
                }
            },
            shape = RoundedCornerShape(20.dp),
            colors = DatePickerDefaults.colors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            DatePickerMy(state)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerMy(state: DatePickerState, modifier: Modifier = Modifier) {
    DatePicker(
        state = state,
        modifier = modifier
            .animateContentSize()
            .fillMaxWidth(),
        title = {},
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            headlineContentColor = MaterialTheme.colorScheme.onBackground,
            weekdayContentColor = MaterialTheme.colorScheme.onBackground,
            subheadContentColor = MaterialTheme.colorScheme.onBackground,
            navigationContentColor = MaterialTheme.colorScheme.onBackground,
            yearContentColor = MaterialTheme.colorScheme.onBackground,
            disabledYearContentColor = MaterialTheme.colorScheme.onBackground,
            currentYearContentColor = MaterialTheme.colorScheme.onBackground,
            selectedYearContentColor = MaterialTheme.colorScheme.onBackground,
            disabledSelectedYearContentColor = MaterialTheme.colorScheme.onBackground,
            selectedYearContainerColor = MaterialTheme.colorScheme.secondary,
            disabledSelectedYearContainerColor = MaterialTheme.colorScheme.onBackground,
            dayContentColor = MaterialTheme.colorScheme.onBackground,
            disabledDayContentColor = MaterialTheme.colorScheme.onBackground,
            selectedDayContentColor = MaterialTheme.colorScheme.onBackground,
            disabledSelectedDayContentColor = MaterialTheme.colorScheme.onBackground,
            selectedDayContainerColor = MaterialTheme.colorScheme.secondary,
            disabledSelectedDayContainerColor = MaterialTheme.colorScheme.onBackground,
            todayContentColor = MaterialTheme.colorScheme.onBackground,
            todayDateBorderColor = MaterialTheme.colorScheme.secondary,
            dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onBackground,
            dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.onBackground,
            dividerColor = MaterialTheme.colorScheme.outline,
        )
    )
}