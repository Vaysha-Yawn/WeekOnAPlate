package week.on.a.plate.menuScreen.view.uiTools.topBar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.menuScreen.logic.MenuEvent
import week.on.a.plate.menuScreen.logic.MenuIUState
import week.on.a.plate.menuScreen.view.uiTools.EditingRow
import week.on.a.plate.menuScreen.view.uiTools.TitleMenuSmall
import week.on.a.plate.ui.theme.ColorButtonNegativeGrey
import week.on.a.plate.ui.theme.ColorStrokeGrey
import week.on.a.plate.ui.theme.ColorTextBlack

@Composable
fun TopBar(weekView: WeekView, title:String, menuIUState: MenuIUState, onEvent: (event: MenuEvent) -> Unit) {
    Column {
        Row(
            Modifier
                .fillMaxWidth().background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (menuIUState.editing.value) {
                EditingRow(actionChooseAll = {
                    onEvent(MenuEvent.ChooseAll)
                }, actionDeleteSelected = {
                    onEvent(MenuEvent.DeleteSelected)
                }, actionSelectedToShopList = {
                    onEvent(MenuEvent.SelectedToShopList)
                }, menuIUState.isAllSelected.value)
            }else{
                TextBodyDisActive(
                    text = if (menuIUState.itsDayMenu.value) "<- Неделя" else "<- День",
                    modifier = Modifier.clickable {
                        onEvent(MenuEvent.SwitchWeekOrDayView)
                    }.padding(vertical = 5.dp).padding(end = 12.dp))
            }
            TitleMenuSmall(title){
                onEvent(MenuEvent.AddRecipeToCategory(weekView.days[menuIUState.activeDayInd.value].date,weekView.days[menuIUState.activeDayInd.value].selections[0].category))
            }
        }
        HorizontalDivider(color = ColorButtonNegativeGrey, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
    }

}