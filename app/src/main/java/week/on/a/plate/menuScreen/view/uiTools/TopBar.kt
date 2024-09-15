package week.on.a.plate.menuScreen.view.uiTools

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
import week.on.a.plate.menuScreen.logic.eventData.ActionMenuDBData
import week.on.a.plate.menuScreen.logic.eventData.DialogData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.menuScreen.logic.eventData.SelectedData
import week.on.a.plate.menuScreen.logic.stateData.MenuIUState
import week.on.a.plate.ui.theme.ColorButtonNegativeGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    menuIUState: MenuIUState,
    onEvent: (event: MenuEvent) -> Unit
) {
    val dateState = rememberDatePickerState()
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
                    onEvent(MenuEvent.ActionSelect(SelectedData.ChooseAll))
                }, actionDeleteSelected = {
                    onEvent(MenuEvent.ActionDBMenu(ActionMenuDBData.DeleteSelected))
                }, actionSelectedToShopList = {
                    onEvent(MenuEvent.OpenDialog(DialogData.SelectedToShopList( onEvent)))
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
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.calendar),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            onEvent(MenuEvent.OpenDialog(DialogData.ChooseDay(dateState, onEvent)))
                        }
                        .padding(6.dp)
                        .size(24.dp)
                )
                TitleMenuSmall(title) {
                    onEvent(
                        MenuEvent.OpenDialog(
                            DialogData.AddPositionNeedSelId(onEvent)
                        )
                    )
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