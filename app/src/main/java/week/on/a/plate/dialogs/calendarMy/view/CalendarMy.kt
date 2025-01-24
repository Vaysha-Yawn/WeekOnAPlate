package week.on.a.plate.dialogs.calendarMy.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorTransparent
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.dialogs.calendarMy.event.CalendarMyEvent
import week.on.a.plate.dialogs.calendarMy.state.StateCalendarMy
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarMy(
    state: StateCalendarMy,
    onEvent: (CalendarMyEvent) -> Unit,
    clickToDay: (date: LocalDate) -> Unit
) {
    val locale = LocalContext.current.resources.configuration.locales[0]
    val cardWidth = 45.dp
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        MonthName(state, locale, onEvent)
        Spacer(Modifier.size(24.dp))
        DaysOfWeek(state, cardWidth)
        MonthDays(state, cardWidth, onEvent, clickToDay)
    }
}

@Composable
private fun MonthName(
    state: StateCalendarMy,
    locale: Locale?,
    onEvent: (CalendarMyEvent) -> Unit
) {
    val month =
        Month.of(state.currentMonth.intValue).getDisplayName(TextStyle.FULL_STANDALONE, locale)
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painterResource(R.drawable.back), "Last month",
            Modifier
                .clickable {
                    onEvent(CalendarMyEvent.LastMonth)
                }
                .size(36.dp))
        TextBody(
            month.capitalize() + ", " + state.currentYear.intValue.toString(),
            textAlign = TextAlign.Center
        )
        Icon(painterResource(R.drawable.forward), "Next month",
            Modifier
                .clickable {
                    onEvent(CalendarMyEvent.NextMonth)
                }
                .size(36.dp))
    }
}

@Composable
private fun DaysOfWeek(
    state: StateCalendarMy,
    cardWidth: Dp
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        for (day in state.firstRow.value) {
            Box(Modifier.weight(1f).height(cardWidth), contentAlignment = Alignment.Center) {
                TextBody(
                    day.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun MonthDays(
    state: StateCalendarMy,
    cardWidth: Dp,
    onEvent: (CalendarMyEvent) -> Unit,
    clickToDay: (date: LocalDate) -> Unit
) {
    var curDay = 0
    val dayInWeek = remember { 7 }
    val days = state.allMonthDayAndIsPlanned.value
    val weeks = (state.allMonthDayAndIsPlanned.value.size / dayInWeek) + 1

    for (week in 0..weeks) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            for (day in state.firstRow.value) {
                if (curDay <= days.lastIndex) {
                    if (day.value != days[curDay].first.dayOfWeek.value) {
                        EmptyCardDayCalendar(this, cardWidth)
                    } else {
                        CardDayCalendar(
                            this,
                            days[curDay].first,
                            cardWidth,
                            state.activeDate.value,
                            days[curDay].second,
                            day.value == state.firstRow.value.last().value,
                        ) {
                            onEvent(CalendarMyEvent.ChangeActiveDate(it))
                            clickToDay(it)
                        }
                        curDay++
                    }
                } else {
                    EmptyCardDayCalendar(this, cardWidth)
                }
            }
        }
    }
}

@Composable
fun EmptyCardDayCalendar(scope: RowScope, size: Dp) {
    with(scope){
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun CardDayCalendar(
    scope: RowScope,
    day: LocalDate,
    size: Dp,
    activeDate: LocalDate,
    isPlanned: Boolean,
    isEndOfWeek: Boolean,
    clickToDay: (date: LocalDate) -> Unit
) {
    with(scope) {
        Box(
            Modifier
                .padding(
                    end = if (!isEndOfWeek) {
                        5.dp
                    } else {
                        0.dp
                    }, bottom = 5.dp
                )
                .weight(1f).height(size)
                .background(
                    if (activeDate == day) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        ColorTransparent
                    }, CircleShape
                )
                .border(
                    2.dp,
                    if (LocalDate.now() == day) {
                        MaterialTheme.colorScheme.primary
                    } else if (isPlanned) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        ColorTransparent
                    }, CircleShape
                )
                .clickable {
                    clickToDay(day)
                }, contentAlignment = Alignment.Center
        ) {
            TextBody(
                day.dayOfMonth.toString(),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Visible
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalendarMy(
) {
    WeekOnAPlateTheme {
        val st = remember { mutableStateOf(LocalDate.of(2024, 9, 5)) }
        val allDays = remember {
            mutableStateOf(
                getMonth(30)
            )
        }
        val firstRow = remember { mutableStateOf(DayOfWeek.entries.toList()) }
        CalendarMy(StateCalendarMy(st, allDays, firstRow), {}, {})
    }
}

private fun getMonth(daysInMonth: Int): List<Pair<LocalDate, Boolean>> {
    val list = mutableListOf<Pair<LocalDate, Boolean>>()
    for (day in 1..daysInMonth) {
        list.add(getDay(day))
    }
    return list
}

private fun getDay(day: Int): Pair<LocalDate, Boolean> {
    return Pair(LocalDate.of(2024, 9, day), false)
}