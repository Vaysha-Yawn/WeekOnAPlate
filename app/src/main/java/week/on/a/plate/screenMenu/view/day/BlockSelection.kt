package week.on.a.plate.screenMenu.view.day

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.core.Event
import week.on.a.plate.screenMenu.state.MenuIUState
import week.on.a.plate.screenMenu.view.topBar.TitleMenu
import week.on.a.plate.screenMenu.view.week.positionsCard.WeekCardPosition

@Composable
fun BlockSelection(
    selection: SelectionView,
    menuIUState: MenuIUState,
    onEvent: (event: Event) -> Unit,
    ) {
    TitleMenu(selection, Modifier.padding(horizontal = 10.dp)
        .padding(start = 20.dp), onEvent)
    Spacer(Modifier.height(10.dp))
    for ((index, i) in selection.positions.withIndex()){
        if (index%2 == 0){
            Row {
                WeekCardPosition(i, Modifier.fillMaxWidth(0.5f).padding(10.dp),  menuIUState= menuIUState, onEvent)
                if (selection.positions.size>index+1){
                    WeekCardPosition(selection.positions[index+1], Modifier.fillMaxWidth().padding(10.dp), menuIUState= menuIUState, onEvent)
                }
            }
        }
    }
    Spacer(Modifier.height(10.dp))
}