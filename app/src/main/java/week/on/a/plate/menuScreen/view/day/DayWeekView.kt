package week.on.a.plate.menuScreen.view.day

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.menuScreen.logic.stateData.MenuIUState


@Composable
fun DayView(day: DayView, menuIUState: MenuIUState, onEvent: (event: MenuEvent) -> Unit) {
    LazyColumn {
        items(day.selections.size) { index ->
            BlockSelection(
                selection = day.selections[index],day.date,
                menuIUState = menuIUState,
                onEvent
            )
        }
    }
}