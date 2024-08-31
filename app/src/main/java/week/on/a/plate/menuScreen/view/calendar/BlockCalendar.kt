package week.on.a.plate.menuScreen.view.calendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import week.on.a.plate.core.data.week.DayView
import java.time.LocalDate

@Composable
fun BlockCalendar(days: MutableList<DayView>, today: LocalDate, activeDayInd: Int, changeDay:(i:Int)->Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.Center) {
        for ((ind, day) in days.withIndex()) {
            CalendarDayCard(
                day.date,
                day.dayInWeek.shortName,
                itToday = (day.date == today),
                itPlanned = (day.selections.any { sel -> sel.recipes.isNotEmpty() }),
                (day == days[activeDayInd]),
                ind,
                changeDay
            )
        }
    }
}