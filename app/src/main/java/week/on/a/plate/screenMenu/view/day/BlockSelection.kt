package week.on.a.plate.screenMenu.view.day

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.core.Event
import week.on.a.plate.screenMenu.event.MenuEvent
import week.on.a.plate.screenMenu.state.MenuIUState
import week.on.a.plate.screenMenu.view.day.positions.CardPosition
import week.on.a.plate.screenMenu.view.topBar.TitleMenu

@Composable
fun BlockSelection(
    selection: SelectionView,
    menuIUState: MenuIUState,
    onEvent: (event: Event) -> Unit,
    ) {
    TitleMenu(selection.name) {
        if (selection.id==0L){
            onEvent(MenuEvent.CreateFirstNonPosedPosition(selection.date))
        }else{
            onEvent(MenuEvent.CreatePosition(selection.id))
        }
    }
    Spacer(Modifier.height(10.dp))
    for (rec in selection.positions) {
        CardPosition(rec, menuIUState, onEvent)
    }
    Spacer(Modifier.height(10.dp))
}