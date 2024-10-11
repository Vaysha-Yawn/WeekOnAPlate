package week.on.a.plate.screenMenu.view.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.data.dataView.example.WeekDataExample
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.theme.ColorButtonNegativeGrey
import week.on.a.plate.core.theme.ColorTextBlack
import week.on.a.plate.core.theme.ColorTransparent
import week.on.a.plate.core.theme.WeekOnAPlateTheme
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
            .padding(end = 3.dp)
            .border(
                1.dp, if (active) {
                    MaterialTheme.colorScheme.onBackground
                } else {
                    ColorTransparent
                }, RoundedCornerShape(10.dp)
            )
            .clickable(onClick = {change(currentInd)})
            .padding(horizontal = 5.dp, vertical = 5.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
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
                        MaterialTheme.colorScheme.primary
                    } else if (itPlanned) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        ColorTransparent
                    }, androidx.compose.foundation.shape.CircleShape
                )
                .padding(5.dp), color = MaterialTheme.colorScheme.onBackground
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