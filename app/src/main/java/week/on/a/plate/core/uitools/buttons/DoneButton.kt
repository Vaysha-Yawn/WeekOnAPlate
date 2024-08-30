package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.ui.theme.ColorButton
import week.on.a.plate.ui.theme.ColorPlan
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun DoneButton(
    click:()->Unit
) {
   Row(Modifier.fillMaxWidth().background(ColorPlan, RoundedCornerShape(50.dp)).padding(10.dp).clickable {
       click()
   }, horizontalArrangement = Arrangement.Center) {
       TextInApp(text = "Готово", textStyle = Typography.bodyLarge)
   }
}

@Preview(showBackground = true)
@Composable
fun PreviewDoneButton() {
    WeekOnAPlateTheme {
        DoneButton {}
    }
}