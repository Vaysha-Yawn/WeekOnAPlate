package week.on.a.plate.screens.base.cookPlanner.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.CookPlannerGroupView
import java.time.LocalDate

@Composable
fun DayViewCookPlan(
    week: Map<LocalDate, List<CookPlannerGroupView>>,
    date: LocalDate,
    onEvent: (Event) -> Unit
) {
    if (week.keys.contains(date) && week[date] != null && week[date]?.isNotEmpty() == true) {
        LazyColumn {
            items(items = week[date]!!, key = { it.id }) { day ->
                CookGroup(day, onEvent)
                Spacer(Modifier.height(12.dp))
            }
        }
    } else {
        EmptyTip()
    }
}