package week.on.a.plate.menuScreen.view.day

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.core.Event
import week.on.a.plate.menuScreen.event.MenuEvent
import week.on.a.plate.menuScreen.state.MenuIUState
import week.on.a.plate.menuScreen.view.day.positions.CardPosition
import week.on.a.plate.menuScreen.view.topBar.TitleMenu

@Composable
fun BlockSelection(
    selection: SelectionView,
    menuIUState: MenuIUState,
    onEvent: (event: Event) -> Unit,
    ) {
    TitleMenu(selection.category) {
        onEvent(MenuEvent.CreatePosition(selection.id))
    }
    Spacer(Modifier.height(10.dp))
    for (rec in selection.positions) {
        CardPosition(rec, menuIUState, onEvent)
    }
    Spacer(Modifier.height(10.dp))
}