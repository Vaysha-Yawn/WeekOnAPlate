package week.on.a.plate.screenMenu.view.day

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.data.dataView.week.DayView
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.PlusButtonTitle
import week.on.a.plate.screenMenu.event.MenuEvent
import week.on.a.plate.screenMenu.state.MenuIUState


@Composable
fun DayView(day: DayView, menuIUState: MenuIUState, onEvent: (event: Event) -> Unit) {
    LazyColumn {
        items(day.selections.size) { index ->
            BlockSelection(
                selection = day.selections[index], menuIUState = menuIUState, onEvent
            )
        }
        item {
            Row(
                Modifier
                    .padding(horizontal = 10.dp)
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextTitle(
                    text = "Добавить приём пищи",
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clickable {
                            onEvent(MenuEvent.CreateSelection(day.date, false))
                        },
                )
                PlusButtonTitle() {
                    onEvent(MenuEvent.CreateSelection(day.date, false))
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}