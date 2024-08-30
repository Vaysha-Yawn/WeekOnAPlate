package week.on.a.plate.menuScreen.view.editDialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.buttons.ButtonText
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun PlaceRecipe() {
    Column( modifier = Modifier.padding(20.dp)) {
        TextInApp(
            "Дублировать Паста в...",
            textStyle = Typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(50.dp))
        ButtonText(
            "Понедельник, 14.08", modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    //
                }
        )
        Spacer(modifier = Modifier.height(30.dp))
        ButtonText(
            "Завтрак", modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    //
                }
        )
        Spacer(modifier = Modifier.height(50.dp))
        DoneButton(){}
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPlaceRecipe() {
    WeekOnAPlateTheme {
        PlaceRecipe()
    }
}