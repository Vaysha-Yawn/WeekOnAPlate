package week.on.a.plate.screens.cookPlanner.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.BuildConfig
import week.on.a.plate.core.Event
import week.on.a.plate.core.ads.NativeAdRow
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
            items(week[date]!!.size) { ind ->
                CookGroup(week[date]!![ind], onEvent)
                Spacer(Modifier.height(12.dp))
                if (ind == 0) {
                    NativeAdRow(BuildConfig.menuDayAdsId)
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    } else {
        EmptyTip()
    }
}