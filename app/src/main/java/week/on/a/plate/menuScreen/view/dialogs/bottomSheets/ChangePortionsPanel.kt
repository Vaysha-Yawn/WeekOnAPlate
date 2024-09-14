package week.on.a.plate.menuScreen.view.dialogs.bottomSheets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.data.example.positionRecipeExample
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.ButtonsCounter
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.menuScreen.logic.eventData.ActionDBData
import week.on.a.plate.menuScreen.logic.eventData.DialogMenuData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun ChangePortionsPanel(data: DialogMenuData.ChangePortionsCount, onEvent: (MenuEvent) -> Unit) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitle(text = stringResource(R.string.Change_number_of_servings))
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.tertiary, RoundedCornerShape(20.dp)),
            horizontalArrangement = Arrangement.Center
        ) {
            ButtonsCounter(data.portionsCount, {
                if (data.portionsCount.intValue > 0) {
                    data.portionsCount.intValue -= 1
                }
            }, {
                if (data.portionsCount.intValue < 20) {
                    data.portionsCount.intValue += 1
                }
            })
        }
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(stringResource(R.string.done)) {
            onEvent(MenuEvent.ActionDBMenu(ActionDBData.ChangePortionsCountDB(data)))
            onEvent(MenuEvent.CloseDialog)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewChangePortionsPanel() {
    WeekOnAPlateTheme {
        val stateBottom = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ChangePortionsPanel(DialogMenuData.ChangePortionsCount(positionRecipeExample, stateBottom)){}
    }
}