package week.on.a.plate.dialogs.changePortions.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.ButtonsCounter
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.theme.WeekOnAPlateTheme

@Composable
fun ChangePortionsPanel(portionsCount: MutableIntState, done: () -> Unit) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp), verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextTitle(text = stringResource(R.string.Change_number_of_servings))
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp)),
            horizontalArrangement = Arrangement.Center
        ) {
            ButtonsCounter(portionsCount, {
                if (portionsCount.intValue > 0) {
                    portionsCount.intValue -= 1
                }
            }, {
                if (portionsCount.intValue < 20) {
                    portionsCount.intValue += 1
                }
            })
        }
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(stringResource(R.string.done), click = done)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChangePortionsPanel() {
    WeekOnAPlateTheme {
        val counter = remember {
            mutableIntStateOf(0)
        }
        ChangePortionsPanel(counter) {}
    }
}