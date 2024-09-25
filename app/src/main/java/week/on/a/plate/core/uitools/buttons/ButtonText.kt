package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import week.on.a.plate.core.theme.ColorButtonNegativeGrey
import week.on.a.plate.core.theme.ColorPanelGreen
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.theme.WeekOnAPlateTheme

//todo delete
@Composable
fun ButtonText(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int? = null,
    textStyle: TextStyle = Typography.bodySmall,
    color: Color = Typography.bodyMedium.color,
    textAlign: TextAlign = TextAlign.Center,
    colorBackground: Color = ColorButtonNegativeGrey,
    click:()->Unit
) {
    TextInApp(
        text, modifier = modifier
            .background(
                colorBackground, RoundedCornerShape(30.dp)
            )
            .padding(horizontal = 6.dp, vertical = 3.dp).clickable {
                click()
            }, textStyle = textStyle,
        maxLines = maxLines, color = color,
        textAlign = textAlign
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewButtonText() {
    WeekOnAPlateTheme {
        ButtonText("Button", colorBackground = ColorPanelGreen, click = {})
    }
}