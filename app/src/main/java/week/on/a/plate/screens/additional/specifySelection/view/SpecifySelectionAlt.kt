package week.on.a.plate.screens.additional.specifySelection.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.app.mainActivity.view.MainEventResolve
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.dialogs.calendarMy.state.StateCalendarMy
import week.on.a.plate.dialogs.calendarMy.view.CalendarMy
import week.on.a.plate.screens.additional.specifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screens.additional.specifySelection.logic.SpecifySelectionViewModel
import week.on.a.plate.screens.additional.specifySelection.state.SpecifySelectionUIState
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun SpecifySelectionAltStart(
    vm: SpecifySelectionViewModel,
    viewModel: MainViewModel
) {
    SpecifySelectionAltContent(vm.state, vm.stateCalendar, ) { vm.onEvent(it) }
    MainEventResolve(vm.mainEvent, vm.dialogOpenParams, viewModel)
}

@Composable
private fun SpecifySelectionAltContent(
    state: SpecifySelectionUIState,
    stateCalendarMy: StateCalendarMy,
    onEvent: (Event) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CloseButton { onEvent(SpecifySelectionEvent.Back) }
            TextTitleItalic(
                text = stringResource(R.string.specify_selection),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        CalendarMy(stateCalendarMy, onEvent){date->
            onEvent(SpecifySelectionEvent.UpdateSelections(context))
            onEvent(SpecifySelectionEvent.ApplyDate(date))
        }
        Spacer(modifier = Modifier.height(24.dp))

        ChooseSelectionSpecifySelection(state, onEvent, this)

        Spacer(modifier = Modifier.height(24.dp))

        val messageError =
            stringResource(id = R.string.message_non_validate_place_position_in_menu)
        DoneButton(
            stringResource(id = R.string.apply),
        ) {
            if (state.checkWeek.value || state.checkDayCategory.value != null) {
                onEvent(SpecifySelectionEvent.Done(context))
            } else {
                onEvent(MainEvent.ShowSnackBar(messageError))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable fun PreviewSpecifySelectionAlt(){
    WeekOnAPlateTheme {
        val st = remember { mutableStateOf(LocalDate.now()) }
        val allDays = remember {
            mutableStateOf(
                listOf(
                    Pair(LocalDate.of(2024, 10, 1), false),
                    Pair(LocalDate.of(2024, 10, 2), false),
                    Pair(LocalDate.of(2024, 10, 3), false),
                    Pair(LocalDate.of(2024, 10, 4), false),
                    Pair(LocalDate.of(2024, 10, 5), false),
                    Pair(LocalDate.of(2024, 10, 6), false),
                )
            )
        }
        val firstRow = remember { mutableStateOf(DayOfWeek.entries.toList()) }
        SpecifySelectionAltContent(SpecifySelectionUIState(), StateCalendarMy(st, allDays, firstRow,)){}
    }
}