package week.on.a.plate.screens.menu.view.topBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.ColorButtonNegativeGrey
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.screens.menu.state.MenuIUState

@Composable
fun TopBar(
    titleWeek: String,
    titleDay: String,
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
                EditingRow(actionDeleteSelected = {
                    onEvent(week.on.a.plate.screens.menu.event.MenuEvent.DeleteSelected)
                }, actionSelectedToShopList = {
                   onEvent(week.on.a.plate.screens.menu.event.MenuEvent.SelectedToShopList)
                }, actionExit = {
                    onEvent(week.on.a.plate.screens.menu.event.MenuEvent.SwitchEditMode)
                })
            } else {
                TextBodyDisActive(
                    text = if (menuIUState.itsDayMenu.value) stringResource(R.string.week_nav) else stringResource(
                        R.string.day_nav
                    ),
                    modifier = Modifier
                        .clickable {
                            onEvent(week.on.a.plate.screens.menu.event.MenuEvent.SwitchWeekOrDayView)
                        }
                        .padding(vertical = 5.dp)
                        .padding(end = 12.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val title = if (menuIUState.itsDayMenu.value) {titleDay} else titleWeek
                    TextBody(text = title, modifier = Modifier.padding(end = 12.dp))
                    Image(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "",
                        modifier = Modifier
                            .clickable {
                                onEvent(week.on.a.plate.screens.menu.event.MenuEvent.ChooseWeek)
                            }
                            .padding(6.dp)
                            .size(24.dp)
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