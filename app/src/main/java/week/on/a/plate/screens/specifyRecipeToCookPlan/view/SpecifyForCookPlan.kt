package week.on.a.plate.screens.specifyRecipeToCookPlan.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.dialogs.calendarMy.state.StateCalendarMy
import week.on.a.plate.dialogs.calendarMy.view.CalendarMy
import week.on.a.plate.screens.specifyRecipeToCookPlan.event.SpecifyRecipeToCookPlanEvent
import week.on.a.plate.screens.specifyRecipeToCookPlan.logic.SpecifyRecipeToCookPlanViewModel
import week.on.a.plate.screens.specifyRecipeToCookPlan.state.SpecifyRecipeToCookPlanUIState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun SpecifyForCookPlanStart(vm: SpecifyRecipeToCookPlanViewModel) {
    SpecifyForCookPlan(vm.state, vm.stateCalendar) { vm.onEvent(it) }
}

@Composable
fun SpecifyForCookPlan(
    state: SpecifyRecipeToCookPlanUIState,
    stateCalendarMy: StateCalendarMy,
    onEvent: (Event) -> Unit
) {
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
            CloseButton { onEvent(SpecifyRecipeToCookPlanEvent.Close) }
            TextTitleItalic(
                text = stringResource(R.string.plan_cook),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        CalendarMy(stateCalendarMy, onEvent) { date ->
            onEvent(SpecifyRecipeToCookPlanEvent.SelectDate(date))
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextBody(stringResource(R.string.time_cook))
        Spacer(modifier = Modifier.height(12.dp))
        val context = LocalContext.current
        CommonButton(
            state.time.value.format(DateTimeFormatter.ofPattern("HH:mm")),
            image = R.drawable.time
        ) {
            onEvent(SpecifyRecipeToCookPlanEvent.OpenTimePick)
        }
        Spacer(modifier = Modifier.height(24.dp))
        TextBody(stringResource(R.string.get_recipe_done))
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Switch(
                state.isStart.value,
                {onEvent(SpecifyRecipeToCookPlanEvent.SwitchStartEnd) },
                colors = SwitchDefaults.colors(
                    uncheckedTrackColor = MaterialTheme.colorScheme.surface,
                    checkedTrackColor = MaterialTheme.colorScheme.secondary,
                    uncheckedBorderColor = MaterialTheme.colorScheme.onSurface,
                    checkedBorderColor = MaterialTheme.colorScheme.secondary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
                    checkedThumbColor = MaterialTheme.colorScheme.surface,
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            if (state.isStart.value) {
                TextBody(stringResource(R.string.by_start))
            } else {
                TextBody(stringResource(R.string.by_end))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        DoneButton(
            stringResource(id = R.string.apply),
        ) {
            onEvent(SpecifyRecipeToCookPlanEvent.Done)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSpecifySelectionAlt() {
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
        SpecifyForCookPlan(
            SpecifyRecipeToCookPlanUIState(),
            StateCalendarMy(st, allDays, firstRow)
        ) {}
    }
}