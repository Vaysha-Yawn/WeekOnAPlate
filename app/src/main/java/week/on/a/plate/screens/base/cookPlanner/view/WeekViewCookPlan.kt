package week.on.a.plate.screens.base.cookPlanner.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.BuildConfig
import week.on.a.plate.core.Event
import week.on.a.plate.core.ads.NativeAdRow
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.screens.base.cookPlanner.state.CookPlannerUIState
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun WeekViewCookPlan(
    state: CookPlannerUIState,
    onEvent: (Event) -> Unit
) {
    NativeAdRow(BuildConfig.cookPlannerAdsIdWeek)
    Spacer(Modifier.height(12.dp))

    LazyColumn {
        for (day in state.week) {
            item {
                Spacer(Modifier.height(24.dp))
                TextTitle(
                    "${
                        day.key.dayOfWeek.getDisplayName(
                            TextStyle.FULL_STANDALONE,
                            Locale.getDefault()
                        )
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                    }, ${day.key.dayOfMonth}",
                    Modifier.padding(start = 24.dp)
                )
            }
            items(day.value) {
                Spacer(Modifier.height(24.dp))
                CookGroup(it, onEvent)
            }
        }
    }
}