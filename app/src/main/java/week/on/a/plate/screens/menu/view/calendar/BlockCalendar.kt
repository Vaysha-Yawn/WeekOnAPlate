package week.on.a.plate.screens.menu.view.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import week.on.a.plate.R
import week.on.a.plate.data.dataView.week.DayView
import java.time.LocalDate

@Composable
fun BlockCalendar(
    days: List<DayView>,
    today: LocalDate,
    activeDayInd: Int,
    changeDay: (i: Int) -> Unit,
    chooseLastWeek:()->Unit,
    chooseNextWeek:()->Unit,
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painterResource(R.drawable.back), "", modifier = Modifier.clickable {
            chooseLastWeek()
        })
        for ((ind, day) in days.withIndex()) {
            CalendarDayCard(
                day.date,
                day.getDyInWeekShort(LocalContext.current.resources.configuration.locales[0]),
                itToday = (day.date == today),
                itPlanned = (day.selections.any { sel -> sel.positions.isNotEmpty() }),
                if (days.size == 7) {
                    (day == days[activeDayInd])
                } else false,
                ind,
                changeDay
            )
        }
        Icon(painterResource(R.drawable.forward), "", modifier = Modifier.clickable {
            chooseNextWeek()
        })
    }
}