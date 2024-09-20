package week.on.a.plate.menuScreen.view.day

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.menuScreen.data.eventData.ActionWeekMenuDB
import week.on.a.plate.menuScreen.data.eventData.MenuEvent
import java.time.LocalDate

@Composable
fun NoDay(data: LocalDate, onEvent:(MenuEvent)->Unit){
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextTitle(text = stringResource(R.string.data_not_found))
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(stringResource(R.string.add_day)) {
            onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.AddEmptyDay(data)))
        }
    }
}