package week.on.a.plate.screens.specifySelection.view.alt

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorTransparent
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.screens.specifySelection.state.StateCalendarMy
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle

@Composable
fun CalendarMy(state: StateCalendarMy,  clickToDay:(date:LocalDate)->Unit) {
    val locale = LocalContext.current.resources.configuration.locales[0]
    val firstRow = StateCalendarMy.getFirstRow(locale)
    val dayInWeek = 7
    val cardWidth = 36.dp
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        val month =
            Month.of(state.currentMonth.intValue).getDisplayName(TextStyle.FULL_STANDALONE, locale)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(painterResource(R.drawable.back), "", Modifier.clickable {
                state.lastMonth()
            })
            TextBody(month.capitalize()+", "+state.currentYear.intValue.toString(), textAlign = TextAlign.Center)
            Icon(painterResource(R.drawable.forward), "", Modifier.clickable {
                state.nextMonth()
            })
        }
        Spacer(Modifier.size(24.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            for (day in firstRow) {
                Box(Modifier.size(cardWidth), contentAlignment = Alignment.Center) {
                    TextBody(day.capitalize(), textAlign = TextAlign.Center)
                }
            }
        }
        var curDay = 0
        for (week in 1..(state.allMonthLocalDate.value.size / dayInWeek) + 1) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                for (day in 1..dayInWeek) {
                    if (curDay >= state.allMonthLocalDate.value.size) {
                        EmptyCardDayCalendar(cardWidth)
                    } else if (state.allMonthLocalDate.value[curDay].dayOfWeek.value == day) {
                        CardDayCalendar(
                            state.allMonthLocalDate.value[curDay],
                            cardWidth,
                            state.activeDate.value
                        ) {
                            state.changeActiveDate(it)
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
    clickToDay: (date: LocalDate) -> Unit
) {
    Box(
        Modifier
            .size(size)
            .background(
                if (activeDate == day) {
                    MaterialTheme.colorScheme.primary
                } else {
                    ColorTransparent
                }, CircleShape
            ).clickable {

                clickToDay(day)
            }, contentAlignment = Alignment.Center
    ) {
        TextBody(
            day.dayOfMonth.toString(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalendarMy() {
    WeekOnAPlateTheme {
        val st = remember { mutableStateOf(LocalDate.now()) }
        CalendarMy(StateCalendarMy(st), {})
    }
}