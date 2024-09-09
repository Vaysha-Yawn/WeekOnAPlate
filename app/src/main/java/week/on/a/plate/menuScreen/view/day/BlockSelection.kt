package week.on.a.plate.menuScreen.view.day

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.menuScreen.logic.MenuEvent
import week.on.a.plate.menuScreen.logic.MenuIUState
import week.on.a.plate.menuScreen.view.day.positions.CardPosition
import week.on.a.plate.menuScreen.view.uiTools.TitleMenu
import java.time.LocalDate

@Composable
fun BlockSelection(
    selection: SelectionView,
    date:LocalDate,
    menuIUState: MenuIUState,
    onEvent: (event: MenuEvent) -> Unit,
    ) {
    TitleMenu(selection.category) {
        onEvent(MenuEvent.AddRecipeToCategory(date, selection.category))
    }
    Spacer(Modifier.height(10.dp))
    for (rec in selection.positions) {
        CardPosition(rec, menuIUState, onEvent)
    }
    Spacer(Modifier.height(10.dp))
}