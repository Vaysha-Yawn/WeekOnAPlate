package week.on.a.plate.menuScreen.view.editDialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.buttons.ButtonText
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.uitools.TextInAppColored
import week.on.a.plate.core.uitools.buttons.ButtonsCounter
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.ui.theme.ColorSecond
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun ChangePortionsPanel() {
    Column( modifier = Modifier.padding(20.dp)) {
        TextInApp(
            "Изменить кол-во порций",
            textStyle = Typography.bodyLarge, textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        val posrc = remember {
            mutableStateOf(0)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            ButtonsCounter(posrc, {}, {})
        }
        Spacer(modifier = Modifier.height(30.dp))
        DoneButton(){}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChangePortionsPanel() {
    WeekOnAPlateTheme {
        ChangePortionsPanel()
    }
}