package week.on.a.plate.menuScreen.view.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.example.WeekDataExample
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.ui.theme.ColorPanel
import week.on.a.plate.ui.theme.ColorPlan
import week.on.a.plate.ui.theme.ColorText
import week.on.a.plate.ui.theme.ColorToday
import week.on.a.plate.ui.theme.ColorTransparent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme
import java.time.LocalDate

@Composable
fun CalendarDayCard(
    dayNumber: LocalDate,
    dayInWeek: String,
    itToday: Boolean,
    itPlanned: Boolean,
    active: Boolean,
    currentInd: Int,
    change: (i: Int) -> Unit
) {
    Column(
        Modifier
            .padding(end = 5.dp)
            .border(
                1.dp, if (active) {
                    ColorText
                } else {
                    ColorTransparent
                }, RoundedCornerShape(10.dp)
            )
            .clickable(onClick = {change(currentInd)})
            .padding(horizontal = 5.dp, vertical = 5.dp), verticalArrangement = Arrangement.Center
    ) {
        TextInApp(
            text = dayInWeek,
            modifier = Modifier
                .padding(horizontal = 4.dp)
        )
        TextInApp(
            dayNumber.dayOfMonth.toString(), modifier = Modifier
                .background(
                    if (itToday) {
                        ColorToday
                    } else if (itPlanned) {
                        ColorPlan
                    } else {
                        ColorPanel
                    }, androidx.compose.foundation.shape.CircleShape
                )
                .padding(5.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCalendarDayCard() {
    val week = WeekDataExample
    WeekOnAPlateTheme {
        Row {
            CalendarDayCard(
                week.days[0].date,
                "пн",
                itToday = false,
                itPlanned = true,
                false,
                0,
            ) {}
            CalendarDayCard(
                week.days[1].date,
                "вт",
                itToday = true,
                itPlanned = true,
                false,
                1,

                ) {}
            CalendarDayCard(
                week.days[2].date,
                "ср",
                itToday = false,
                itPlanned = false,
                false,
                2,

                ) {}
            CalendarDayCard(
                week.days[3].date,
                "чт",
                itToday = false,
                itPlanned = true,
                true,
                3,

                ) {}
        }
    }
}