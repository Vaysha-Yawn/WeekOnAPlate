package week.on.a.plate.screenMenu.view.topBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.screenMenu.event.ActionWeekMenuDB
import week.on.a.plate.core.Event
import week.on.a.plate.screenMenu.event.MenuEvent
import week.on.a.plate.screenMenu.event.SelectedEvent
import week.on.a.plate.screenMenu.state.MenuIUState
import week.on.a.plate.core.theme.ColorButtonNegativeGrey

@Composable
fun TopBar(
    title: String,
    menuIUState: MenuIUState,
    onEvent: (event: Event) -> Unit
) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (menuIUState.editing.value) {
                EditingRow(actionChooseAll = {
                    onEvent(MenuEvent.ActionSelect(SelectedEvent.ChooseAll))
                }, actionDeleteSelected = {
                    onEvent(MenuEvent.DeleteSelected)
                }, actionSelectedToShopList = {
                   // onEvent(MainEvent.OpenDialog(DialogData.SelectedToShopList( onEvent)))
                }, menuIUState.isAllSelected.value)
            } else {
                TextBodyDisActive(
                    text = if (menuIUState.itsDayMenu.value) stringResource(R.string.week_nav) else stringResource(
                        R.string.day_nav
                    ),
                    modifier = Modifier
                        .clickable {
                            onEvent(MenuEvent.SwitchWeekOrDayView)
                        }
                        .padding(vertical = 5.dp)
                        .padding(end = 12.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "",
                        modifier = Modifier
                            .clickable {
                                onEvent(MenuEvent.ChooseWeek)
                            }
                            .padding(6.dp)
                            .size(24.dp)
                    )
                    TitleMenuSmall(title) {
                        onEvent(MenuEvent.GetSelIdAndCreate)
                    }
                }
            }
        }
        HorizontalDivider(
            color = ColorButtonNegativeGrey,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
    }

}