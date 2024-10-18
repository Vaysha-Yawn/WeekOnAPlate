package week.on.a.plate.screens.cookPlanner.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.screens.cookPlanner.event.CookPlannerEvent
import week.on.a.plate.screens.cookPlanner.logic.CookPlannerViewModel
import week.on.a.plate.screens.cookPlanner.state.CookPlannerUIState

@Composable
fun CookPlannerStart(vm:CookPlannerViewModel){
    CookPlannerContent(vm.state) { vm.onEvent(it) }
}

@Composable
fun CookPlannerContent(state:CookPlannerUIState, onEvent:(CookPlannerEvent)->Unit){
    //todo
}

@Preview (showBackground = true)
@Composable fun PreviewCookPlanner(){
    WeekOnAPlateTheme {
        CookPlannerContent(CookPlannerUIState()){}
    }
}