package week.on.a.plate.screenMenu.view.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import week.on.a.plate.data.dataView.week.DayView
import java.time.LocalDate

@Composable
fun BlockCalendar(days: List<DayView>, today: LocalDate, activeDayInd: Int, changeDay:(i:Int)->Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Center) {
        for ((ind, day) in days.withIndex()) {
            CalendarDayCard(
                day.date,
                day.dayInWeek.shortName,
                itToday = (day.date == today),
                itPlanned = (day.selections.any { sel -> sel.positions.isNotEmpty() }),
                if (days.size == 7){
                    (day == days[activeDayInd])
                }else false,
                ind,
                changeDay
            )
        }
    }
}