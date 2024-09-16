package week.on.a.plate.menuScreen.view.day

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.menuScreen.data.eventData.DialogData
import week.on.a.plate.menuScreen.data.eventData.MenuEvent
import week.on.a.plate.menuScreen.data.stateData.MenuIUState
import week.on.a.plate.menuScreen.view.day.positions.CardPosition
import week.on.a.plate.menuScreen.view.topBar.TitleMenu
import java.time.LocalDate

@Composable
fun BlockSelection(
    selection: SelectionView,
    date:LocalDate,
    menuIUState: MenuIUState,
    onEvent: (event: MenuEvent) -> Unit,
    ) {
    TitleMenu(selection.category) {
        onEvent(MenuEvent.OpenDialog(DialogData.AddPosition( selection.id, onEvent)))
    }
    Spacer(Modifier.height(10.dp))
    for (rec in selection.positions) {
        CardPosition(rec, menuIUState, onEvent)
    }
    Spacer(Modifier.height(10.dp))
}