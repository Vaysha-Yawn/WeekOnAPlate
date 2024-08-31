package week.on.a.plate.menuScreen.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.menuScreen.logic.MenuViewModel
import week.on.a.plate.menuScreen.view.calendar.BlockCalendar
import week.on.a.plate.menuScreen.view.dayweekview.DayView
import week.on.a.plate.menuScreen.view.dayweekview.WeekView
import week.on.a.plate.menuScreen.view.uiTools.ButtonMenuNav
import week.on.a.plate.menuScreen.view.uiTools.EditingRow
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun MenuScreen(vm: MenuViewModel) {
    Column(Modifier.padding(top = 30.dp)) {
        Row(
            Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 20.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            ButtonMenuNav(itsDayMenu = vm.itsDayMenu.value) {
                vm.switchWeekOrDayView()
                vm.itsDayMenu.value = !vm.itsDayMenu.value
            }
        }
        if (vm.itsDayMenu.value) {
            BlockCalendar(vm.week.value.days, vm.today, vm.activeDayInd.intValue) { ind ->
                vm.activeDayInd.intValue = ind
            }
            Spacer(Modifier.height(20.dp))
        }
        if (vm.editing.value) {
            EditingRow(actionChooseAll = {
                vm.actionChooseAll()
            }, actionDeleteSelected = {
                vm.actionDeleteSelected()
            }, actionSelectedToShopList = {
                vm.actionSelectedToShopList()
            })
            Spacer(Modifier.height(10.dp))
        }
        if (vm.itsDayMenu.value) {
            DayView(vm.week.value!!.days[vm.activeDayInd.intValue], vm.editing, vm)
        } else {
            WeekView(vm.week.value!!, vm.editing, vm)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    //val vm = MenuViewModel()
    WeekOnAPlateTheme {
       // MenuScreen(vm)
    }
}