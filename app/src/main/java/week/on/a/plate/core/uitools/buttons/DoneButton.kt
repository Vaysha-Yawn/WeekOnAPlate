package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.ui.theme.ColorButtonGreen
import week.on.a.plate.ui.theme.ColorButtonNegativeGrey
import week.on.a.plate.ui.theme.ColorTextBlack
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.ui.theme.WeekOnAPlateTheme
import week.on.a.plate.ui.theme.titleMediumItalic

@Composable
fun DoneButton(
    text: String,
    modifier: Modifier = Modifier,
    click: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth()
            .background(ColorButtonGreen, RoundedCornerShape(20.dp))
            .padding(10.dp)
            .clickable {
                click()
            }, horizontalArrangement = Arrangement.Center
    ) {
        TextInApp(text = text, textStyle = Typography.titleLarge, color = ColorTextBlack)
    }
}

@Composable
fun CommonButton(
    text: String,
    image:Int? = null,
    click: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(ColorButtonNegativeGrey, RoundedCornerShape(20.dp))
            .padding(10.dp)
            .clickable {
                click()
            }, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier)
        TextInApp(text = text, textStyle = titleMediumItalic, color = ColorTextBlack)
        if (image==null){
            Spacer(modifier = Modifier)
        }else{
            Image(
                painter = painterResource(id = image),
                contentDescription = "",
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDoneButton() {
    WeekOnAPlateTheme {
        Column {
            DoneButton("Готово") {}
            CommonButton("Нет") {}
        }
    }
}