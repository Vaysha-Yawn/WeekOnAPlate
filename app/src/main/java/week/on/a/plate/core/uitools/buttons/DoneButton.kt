package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.ui.theme.ColorButtonGreen
import week.on.a.plate.ui.theme.ColorButtonNegativeGrey
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun DoneButton(
    click: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(ColorButtonGreen, RoundedCornerShape(20.dp))
            .padding(10.dp)
            .clickable {
                click()
            }, horizontalArrangement = Arrangement.Center
    ) {
        TextInApp(text = "Готово", textStyle = Typography.titleLarge)
    }
}

@Composable
fun CommonButton(
    click: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(ColorButtonNegativeGrey, RoundedCornerShape(20.dp))
            .padding(10.dp)
            .clickable {
                click()
            }, horizontalArrangement = Arrangement.Center
    ) {
        TextInApp(text = "Нет", textStyle = Typography.titleLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDoneButton() {
    WeekOnAPlateTheme {
        Column {
            DoneButton {}
            CommonButton{}
        }
    }
}