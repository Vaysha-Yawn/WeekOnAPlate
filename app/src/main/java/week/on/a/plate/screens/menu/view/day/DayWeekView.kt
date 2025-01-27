package week.on.a.plate.screens.menu.view.day

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.data.dataView.week.DayView
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.PlusButtonTitle
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.screens.menu.state.MenuUIState


@Composable
fun DayView(day: DayView, menuUIState: MenuUIState, onEvent: (event: Event) -> Unit) {
    val context = LocalContext.current
    LazyColumn(Modifier.fillMaxWidth()) {
        items(day.selections.size) { index ->
            BlockSelection(
                selection = day.selections[index], menuUIState = menuUIState, onEvent, index==0
            )
            Spacer(modifier = Modifier.size(12.dp))
        }
        item {
            Row(
                Modifier
                    .padding(horizontal = 10.dp)
                    .padding(start = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextTitle(
                    text = stringResource(R.string.add_meal),
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .clickNoRipple {
                            onEvent(week.on.a.plate.screens.menu.event.MenuEvent.CreateSelection(day.date, false, context))
                        },
                )
                PlusButtonTitle() {
                    onEvent(week.on.a.plate.screens.menu.event.MenuEvent.CreateSelection(day.date, false, context))
                }
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}