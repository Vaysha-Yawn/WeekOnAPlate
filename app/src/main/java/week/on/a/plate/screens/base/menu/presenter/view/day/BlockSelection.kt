package week.on.a.plate.screens.base.menu.presenter.view.day

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TitleMenu
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.screens.base.menu.presenter.state.MenuUIState
import week.on.a.plate.screens.base.menu.presenter.view.day.positionsCard.WeekCardPosition
import java.time.format.DateTimeFormatter

@Composable
fun BlockSelection(
    selection: SelectionView,
    menuUIState: MenuUIState,
    onEvent: (event: Event) -> Unit,
    ) {
    Column(Modifier.fillMaxWidth()) {
        if (selection.dateTime.hour > 0){
            SubText(text = selection.dateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                modifier = Modifier.padding(start = 30.dp))
            Spacer(Modifier.height(6.dp))
        }
        TitleMenu(selection,
            Modifier
                .padding(horizontal = 10.dp)
                .padding(start = 20.dp), onEvent)
        Spacer(Modifier.height(10.dp))
        for ((index, i) in selection.positions.withIndex()){
            if (index%2 == 0){
                Row {
                    WeekCardPosition(i,
                        Modifier
                            .fillMaxWidth(0.5f)
                            .padding(10.dp),  menuUIState = menuUIState, onEvent)
                    if (selection.positions.size>index+1){
                        WeekCardPosition(selection.positions[index+1],
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp), menuUIState= menuUIState, onEvent)
                    }
                }
            }
        }
        Spacer(Modifier.height(10.dp))
    }
}