package week.on.a.plate.screens.base.wrapperDatePicker.view.calendar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.utils.getDayInWeekShort
import week.on.a.plate.screens.base.wrapperDatePicker.state.WrapperDatePickerUIState
import java.time.LocalDate

@Composable
fun BlockCalendar(
    daysAndIsPlanned: List<Pair<LocalDate, Boolean>>,
    state: WrapperDatePickerUIState,
    changeDay: (i: Int) -> Unit,
    chooseLastWeek:()->Unit,
    chooseNextWeek:()->Unit,
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painterResource(R.drawable.back), "Last week", modifier = Modifier
            .clickable {
                chooseLastWeek()
            }
            .size(36.dp))
        for ((ind, day) in daysAndIsPlanned.withIndex()) {
            CalendarDayCard(
                day.first,
                day.first.getDayInWeekShort(LocalContext.current.resources.configuration.locales[0]),
                itToday = (day.first == LocalDate.now()),
                itPlanned = day.second,
                if (daysAndIsPlanned.size == 7) { state.activeDay.value == day.first
                } else false,
                ind,
                changeDay
            )
        }
        Icon(painterResource(R.drawable.forward), "Next week", modifier = Modifier
            .clickable {
                chooseNextWeek()
            }
            .size(36.dp))
    }
}