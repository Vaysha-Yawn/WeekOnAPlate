package week.on.a.plate.dialogs.calendarMy.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
    val cardWidth = 36.dp
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
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Icon(painterResource(R.drawable.back), "", Modifier.clickable {
            onEvent(CalendarMyEvent.LastMonth)
        })
        TextBody(
            month.capitalize() + ", " + state.currentYear.intValue.toString(),
            textAlign = TextAlign.Center
        )
        Icon(painterResource(R.drawable.forward), "", Modifier.clickable {
            onEvent(CalendarMyEvent.NextMonth)
        })
    }
}

@Composable
private fun DaysOfWeek(
    state: StateCalendarMy,
    cardWidth: Dp
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        for (day in state.firstRow.value) {
            Box(Modifier.size(cardWidth), contentAlignment = Alignment.Center) {
                TextBody(
                    day.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
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
    val dayInWeek = 7
    for (week in 0..(state.allMonthDayAndIsPlanned.value.size / dayInWeek) + 1) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            for (day in 1..dayInWeek) {
                if (curDay >= state.allMonthDayAndIsPlanned.value.size) {
                    EmptyCardDayCalendar(cardWidth)
                } else if (state.allMonthDayAndIsPlanned.value[curDay].first.dayOfWeek.value == day) {
                    CardDayCalendar(
                        state.allMonthDayAndIsPlanned.value[curDay].first,
                        cardWidth,
                        state.activeDate.value,
                        state.allMonthDayAndIsPlanned.value[curDay].second,
                        day == dayInWeek
                    ) {
                        onEvent(CalendarMyEvent.ChangeActiveDate(it))
                        clickToDay(it)
                    }
                    curDay++
                } else {
                    EmptyCardDayCalendar(cardWidth)
                }
            }
        }
    }
}

@Composable
fun EmptyCardDayCalendar(size: Dp) {
    Spacer(Modifier.size(size))
}

@Composable
fun CardDayCalendar(
    day: LocalDate,
    size: Dp,
    activeDate: LocalDate,
    isPlanned: Boolean,
    isEndOfWeek:Boolean,
    clickToDay: (date: LocalDate) -> Unit
) {
    Box(
        Modifier.padding(end = if(!isEndOfWeek) {
            5.dp
        }else{0.dp}, bottom = 5.dp)
            .size(size)
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

@Preview(showBackground = true)
@Composable
fun PreviewCalendarMy(
) {
    WeekOnAPlateTheme {
        val st = remember { mutableStateOf(LocalDate.of(2024, 9,5)) }
        val allDays = remember {
            mutableStateOf(
                getMonth(30)
            )
        }
        val firstRow = remember { mutableStateOf(listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")) }
        CalendarMy(StateCalendarMy(st, allDays, firstRow), {}, {})
    }
}

private fun getMonth(daysInMonth:Int): List<Pair<LocalDate, Boolean>> {
    val list = mutableListOf<Pair<LocalDate, Boolean>>()
    for (day in 1..daysInMonth){
        list.add(getDay(day))
    }
    return list
}

private fun getDay(day:Int):Pair<LocalDate, Boolean>{
    return Pair(LocalDate.of(2024, 9, day), false)
}