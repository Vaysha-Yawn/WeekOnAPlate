package week.on.a.plate.screens.base.wrapperDatePicker.view.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.ColorTransparent
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.data.dataView.example.WeekDataExample
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
            .defaultMinSize(minWidth = 48.dp)
            .padding(end = 3.dp)
            .border(
                1.dp, if (active) {
                    MaterialTheme.colorScheme.onBackground
                } else {
                    ColorTransparent
                }, RoundedCornerShape(10.dp)
            )
            .clickable(onClick = { change(currentInd) })
            .padding(5.dp,), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInApp(
            text = dayInWeek,
            modifier = Modifier
                .padding(horizontal = 3.dp)
        )
        TextInApp(
            dayNumber.dayOfMonth.toString(), modifier = Modifier
                .background(
                    if (active) {
                        MaterialTheme.colorScheme.surface
                    } else {
                        ColorTransparent
                    }, CircleShape
                )
                .border(
                    2.dp,
                    if (itToday) {
                        MaterialTheme.colorScheme.primary
                    } else if (itPlanned) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        ColorTransparent
                    }, CircleShape
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
                LocalDate.of(2025, 1, 20),
                "пн",
                itToday = false,
                itPlanned = true,
                false,
                0,
            ) {}
            CalendarDayCard(
                LocalDate.of(2025, 1, 21),
                "вт",
                itToday = true,
                itPlanned = true,
                false,
                1,

                ) {}
            CalendarDayCard(
                LocalDate.of(2025, 1, 22),
                "ср",
                itToday = false,
                itPlanned = false,
                false,
                2,

                ) {}
            CalendarDayCard(
                LocalDate.of(2025, 1, 23),
                "чт",
                itToday = false,
                itPlanned = true,
                true,
                3,

                ) {}
        }
    }
}