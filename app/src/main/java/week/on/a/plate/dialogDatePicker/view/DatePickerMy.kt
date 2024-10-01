package week.on.a.plate.dialogDatePicker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import week.on.a.plate.R
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.theme.ColorButtonGreen
import week.on.a.plate.core.theme.ColorTextBlack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerMy(
    state: DatePickerState,
    showState: MutableState<Boolean>,
    onClose: () -> Unit,
    done: () -> Unit
) {
    if (showState.value) {
        Dialog(onDismissRequest = {
            onClose()
        }) {
            Column(
                Modifier
                    .background(
                        MaterialTheme.colorScheme.background,
                        RoundedCornerShape(20.dp)
                    )
                    .padding(10.dp)
            ) {
                DatePicker(
                    state = state, colors = DatePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        headlineContentColor = MaterialTheme.colorScheme.onBackground,
                        weekdayContentColor = MaterialTheme.colorScheme.onBackground,
                        subheadContentColor = MaterialTheme.colorScheme.onBackground,
                        navigationContentColor = MaterialTheme.colorScheme.onBackground,
                        yearContentColor = MaterialTheme.colorScheme.onBackground,
                        disabledYearContentColor = MaterialTheme.colorScheme.onBackground,
                        currentYearContentColor = MaterialTheme.colorScheme.onBackground,
                        selectedYearContentColor = ColorTextBlack,
                        disabledSelectedYearContentColor = MaterialTheme.colorScheme.onBackground,
                        selectedYearContainerColor = ColorButtonGreen,
                        disabledSelectedYearContainerColor = MaterialTheme.colorScheme.onBackground,
                        dayContentColor = MaterialTheme.colorScheme.onBackground,
                        disabledDayContentColor = MaterialTheme.colorScheme.onBackground,
                        selectedDayContentColor = ColorTextBlack,
                        disabledSelectedDayContentColor = MaterialTheme.colorScheme.onBackground,
                        selectedDayContainerColor = ColorButtonGreen,
                        disabledSelectedDayContainerColor = MaterialTheme.colorScheme.onBackground,
                        todayContentColor = MaterialTheme.colorScheme.onBackground,
                        todayDateBorderColor = ColorButtonGreen,
                        dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onBackground,
                        dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.onBackground,
                        dividerColor = MaterialTheme.colorScheme.outline,
                    )
                )
                DoneButton(text = stringResource(id = R.string.apply), Modifier.padding(12.dp)) {
                    done()
                }
            }
        }
    }
}