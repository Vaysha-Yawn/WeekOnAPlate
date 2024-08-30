package week.on.a.plate.core.uitools

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.ui.theme.ColorSecond
import week.on.a.plate.ui.theme.ColorText
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.ui.theme.WeekOnAPlateTheme


@Composable
fun TextInApp(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int? = null,
    textStyle: TextStyle = Typography.bodySmall,
    color: Color = Typography.bodySmall.color,
    textAlign: TextAlign = TextAlign.Left
) {
    Text(
        text = text,
        color = color,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines ?: 20,
        modifier = modifier,
        style = textStyle,
        textAlign = textAlign
    )
}

@Composable
fun TextInAppColored(
    text: String,
    colorBackground: Color,
    modifier: Modifier = Modifier,
    maxLines: Int? = null,
    textStyle: TextStyle = Typography.bodySmall,
    color: Color = Typography.bodySmall.color,
    textAlign: TextAlign = TextAlign.Left,
) {
    TextInApp(
        text, modifier = modifier
            .background(
                colorBackground, RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 5.dp), textStyle = textStyle,
        maxLines = maxLines, color = color,
        textAlign = textAlign
    )
}


@Composable
fun TextInAppColoredWithBorder(
    text: String,
    colorBackground: Color,
    borderColor: Color,
    modifier: Modifier = Modifier,
    maxLines: Int? = null,
    textStyle: TextStyle = Typography.bodySmall,
    color: Color = Typography.bodySmall.color,
    textAlign: TextAlign = TextAlign.Left,
) {
    TextInApp(
        text, modifier = modifier
            .background(
                colorBackground, RoundedCornerShape(10.dp)
            )
            .border(BorderStroke(1.dp, borderColor), RoundedCornerShape(10.dp))
            .padding(horizontal = 10.dp, vertical = 2.dp), textStyle = textStyle, maxLines = maxLines, color = color,
        textAlign = textAlign
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPlusButton() {
    WeekOnAPlateTheme {
        Column {
            TextInApp("Text 1")
            TextInAppColored("Text 2", ColorSecond)
            TextInAppColoredWithBorder("Text 3", ColorSecond, ColorText)
        }
    }
}