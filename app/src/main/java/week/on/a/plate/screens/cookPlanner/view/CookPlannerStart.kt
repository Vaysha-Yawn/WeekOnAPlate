package week.on.a.plate.screens.cookPlanner.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.ads.NativeAdRow
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.wrapperDatePicker.view.WrapperDatePicker
import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.screens.cookPlanner.logic.CookPlannerViewModel
import week.on.a.plate.screens.cookPlanner.state.CookPlannerUIState
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CookPlannerStart(vm: CookPlannerViewModel) {
    CookPlannerContent(vm.state) { vm.onEvent(it) }
}

@Composable
fun CookPlannerContent(state: CookPlannerUIState, onEvent: (Event) -> Unit) {
    val weekResult = state.week.value.map {
        val date = it.key
        val isPlanned = it.value.isNotEmpty()
        Pair(date, isPlanned)
    }
    WrapperDatePicker(
        state.wrapperDatePickerUIState,
        state.wrapperDatePickerUIState.isGroupSelectedModeActive,
        weekResult,
        onEvent,
        {
            DayViewCookPlan(
                state.week.value,
                state.wrapperDatePickerUIState.activeDay.value,
                onEvent
            )
        },
        {
            NativeAdRow("R-M-13419544-2")
            Spacer(Modifier.height(12.dp))

            LazyColumn {
                for (day in state.week.value) {
                    item {
                        Spacer(Modifier.height(24.dp))
                        TextTitle(
                            "${
                                day.key.dayOfWeek.getDisplayName(
                                    TextStyle.SHORT,
                                    Locale.getDefault()
                                )
                                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                            }, ${day.key.dayOfMonth}",
                            Modifier.padding(start = 24.dp)
                        )
                    }
                    items(day.value) {
                        Spacer(Modifier.height(24.dp))
                        CookGroup(it, onEvent)
                    }
                }
            }
        })
}

@Composable
fun DayViewCookPlan(
    week: Map<LocalDate, List<CookPlannerGroupView>>,
    date: LocalDate,
    onEvent: (Event) -> Unit
) {
    if (week.keys.contains(date) && week[date] != null && week[date]?.isNotEmpty() == true) {
        LazyColumn {
            items(week[date]!!.size) { ind ->
                CookGroup(week[date]!![ind], onEvent)
                Spacer(Modifier.height(12.dp))
                if (ind == 0) {
                    NativeAdRow("R-M-13419544-3")
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    } else {
        EmptyTip()
    }
}

@Composable
fun EmptyTip() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        TextTitle(stringResource(R.string.seems_empty))
        Spacer(Modifier.height(24.dp))
        TextBody(stringResource(R.string.cook_planner_empty_tip))
        Spacer(Modifier.height(24.dp))
        NativeAdRow("R-M-13419544-6")
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCookPlanner() {
    WeekOnAPlateTheme {
        CookPlannerContent(CookPlannerUIState()) {}
    }
}