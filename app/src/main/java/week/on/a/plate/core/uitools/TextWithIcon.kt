package week.on.a.plate.core.uitools

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.theme.WeekOnAPlateTheme

@Composable
fun TextAnnotated(text: AnnotatedString, inlineContent: Map<String, InlineTextContent> = mapOf()) {
    Text(
        text = text,
        style = Typography.bodyMedium,
        inlineContent = inlineContent,
        lineHeight = 24.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
    )
}


@Composable
fun TextWithIcon() {
    val backId = "backId"
    val nextId = "nextId"
    val refreshId = "refreshId"
    val text = buildAnnotatedString {
        append("Используйте кнопки ")
        appendInlineContent(backId, "[icon back]")
        append(" ")
        appendInlineContent(nextId, "[icon next]")
        append(" , ")
        appendInlineContent(refreshId, "[icon refresh]")
        append(" или “Искать заново” для ")
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("удобного поиска.")
        }
    }
    val inlineContent = mapOf(
        Pair(
            backId,
            InlineTextContent(
                Placeholder(
                    width = 20.sp,
                    height = 20.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Icon(Icons.Rounded.ArrowBack, "icon back")
            }
        ),
        Pair(
            nextId,
            InlineTextContent(
                Placeholder(
                    width = 20.sp,
                    height = 20.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Icon(Icons.Rounded.ArrowForward, "icon next")
            }
        ),
        Pair(
            refreshId,
            InlineTextContent(
                Placeholder(
                    width = 20.sp,
                    height = 20.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Icon(Icons.Rounded.Refresh, "icon refresh")
                //Icon(painterResource(R.drawable.more), "")
            }
        ),
    )
    Text(
        text = text,
        inlineContent = inlineContent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTextWithIcon() {
    WeekOnAPlateTheme {
        TextWithIcon()
    }
}