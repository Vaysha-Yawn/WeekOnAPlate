package week.on.a.plate.screenMenu.view.week

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.PlusButtonTitle
import week.on.a.plate.core.Event
import week.on.a.plate.screenMenu.event.MenuEvent
import week.on.a.plate.screenMenu.state.MenuIUState
import week.on.a.plate.screenMenu.view.week.positions.WeekCardPosition

@Composable
fun WeekMenu(
    menuIUState: MenuIUState,
    onEvent: (event: Event) -> Unit,
    week: WeekView
) {
    LazyColumn(Modifier.padding(horizontal = 12.dp)) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Row {
                TextTitle(text = stringResource(R.string.for_week))
                Spacer(modifier = Modifier.width(24.dp))
                LazyRow {
                    items(week.selection.positions.size) {
                        WeekCardPosition(
                            position = week.selection.positions[it],
                            menuIUState = menuIUState,
                            onEvent
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
        }
        items(week.days.size) { dayId ->
            val day = week.days[dayId]
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextTitle(text = day.dayInWeek.shortName)
                    TextTitle(text = day.date.dayOfMonth.toString())
                }
                Spacer(modifier = Modifier.size(12.dp))
                Column(Modifier.animateContentSize()) {
                    for (sel in day.selections){
                        Spacer(modifier = Modifier.size(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))) {
                                SubText(
                                    text = sel.category,
                                    modifier = Modifier.width(170.dp),
                                )
                                PlusButtonTitle {
                                    onEvent(MenuEvent.CreatePosition(sel.id))
                                }
                            }
                            sel.positions.forEach { pos ->
                                Spacer(modifier = Modifier.size(12.dp))
                                WeekCardPosition(
                                    position = pos,
                                    menuIUState = menuIUState,
                                    onEvent
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
        }
    }
}