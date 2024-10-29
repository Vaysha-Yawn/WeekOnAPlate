package week.on.a.plate.screens.wrapperDatePicker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.utils.dateToString
import week.on.a.plate.screens.menu.event.MenuEvent
import week.on.a.plate.screens.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.wrapperDatePicker.state.WrapperDatePickerUIState
import week.on.a.plate.screens.wrapperDatePicker.view.calendar.BlockCalendar
import week.on.a.plate.screens.wrapperDatePicker.view.topBar.TopBar
import java.time.LocalDate

@Composable
fun WrapperDatePicker(
    uiState: WrapperDatePickerUIState, editing: MutableState<Boolean>,
    week: List<Pair<LocalDate, Boolean>>,
    onEvent: (event: Event) -> Unit, dayContent: @Composable ()->Unit, weekContent: @Composable ()->Unit,
) {
    if (week.size<7)return
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 10.dp)
    ) {
        val weekTitle = uiState.titleTopBar.value
        val curDay = week[uiState.activeDayInd.value].first.dateToString()
        TopBar(weekTitle, curDay, editing, uiState,
            actionDeleteSelected = { onEvent(MenuEvent.DeleteSelected) },
            actionSelectedToShopList = { onEvent(MenuEvent.SelectedToShopList) }, onEvent
        )
        if (uiState.itsDayMenu.value) {
            Spacer(modifier = Modifier.height(12.dp))
            val daysForCalendar = week.map {
                val date = it.first
                val isPlanned = it.second
                Pair(date, isPlanned)
            }
            BlockCalendar(daysForCalendar, uiState, changeDay = { ind ->
                uiState.activeDayInd.value = ind
                uiState.activeDay.value = week[ind].first
            }, chooseLastWeek = {
                onEvent(
                    WrapperDatePickerEvent.ChangeWeek(
                        week[uiState.activeDayInd.value].first.minusWeeks(
                            1
                        )
                    )
                )
            }, chooseNextWeek = {
                onEvent(
                    WrapperDatePickerEvent.ChangeWeek(
                        week[uiState.activeDayInd.value].first.plusWeeks(
                            1
                        )
                    )
                )
            })
            Spacer(Modifier.height(24.dp))
        }

        if (uiState.itsDayMenu.value) {
            dayContent()
        } else {
            weekContent()
        }
    }
}