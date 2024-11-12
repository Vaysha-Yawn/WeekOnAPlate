package week.on.a.plate.screens.cookPlanner.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextDisplayItalic
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.TextTitleLarge
import week.on.a.plate.core.uitools.TextTitleLargeItalic
import week.on.a.plate.data.dataView.CookPlannerStepView
import week.on.a.plate.screens.cookPlanner.logic.CookPlannerViewModel
import week.on.a.plate.screens.cookPlanner.state.CookPlannerUIState
import week.on.a.plate.screens.wrapperDatePicker.view.WrapperDatePicker
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
            LazyColumn {
                for (day in state.week.value) {
                    item {
                        Spacer(Modifier.height(24.dp))
                        TextTitle(
                            "${
                                day.key.dayOfWeek.getDisplayName(
                                    TextStyle.SHORT,
                                    Locale.getDefault()
                                ).capitalize()
                            }, ${day.key.dayOfMonth}",
                            Modifier.padding(start = 24.dp)
                        )
                    }
                    items(day.value) {
                        Spacer(Modifier.height(24.dp))
                        CardStep(it, onEvent)
                    }
                }
            }
        })
}

@Composable
fun DayViewCookPlan(
    week: Map<LocalDate, List<CookPlannerStepView>>,
    date: LocalDate,
    onEvent: (Event) -> Unit
) {
    if (week.keys.contains(date) && week[date] != null && week[date]?.isNotEmpty()==true) {
        LazyColumn {
            items(week[date]!!.size) { ind ->
                CardStep(week[date]!![ind], onEvent)
                Spacer(Modifier.height(24.dp))
            }
        }
    } else {
        EmptyTip()
    }
}

@Composable
fun EmptyTip() {
    Column (Modifier.fillMaxSize().padding( horizontal = 24.dp)) {
        TextTitle("Похоже здесь пусто..")
        Spacer(Modifier.height(24.dp))
        TextSmall("Запланируйте приготовление через: Рецепт в меню -> Больше -> Планировать приготовление")
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCookPlanner() {
    WeekOnAPlateTheme {
        CookPlannerContent(CookPlannerUIState()) {}
    }
}