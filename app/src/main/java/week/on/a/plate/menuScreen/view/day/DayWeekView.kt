package week.on.a.plate.menuScreen.view.day

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.menuScreen.data.eventData.MenuEvent
import week.on.a.plate.menuScreen.data.stateData.MenuIUState


@Composable
fun DayView(day: DayView, menuIUState: MenuIUState, onEvent: (event: Event) -> Unit) {
    LazyColumn {
        items(day.selections.size) { index ->
            BlockSelection(
                selection = day.selections[index], menuIUState = menuIUState,
                onEvent
            )
        }
    }
}