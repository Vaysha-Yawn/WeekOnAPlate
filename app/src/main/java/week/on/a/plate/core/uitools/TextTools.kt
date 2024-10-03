package week.on.a.plate.core.uitools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.core.theme.ColorButtonGreen
import week.on.a.plate.core.theme.ColorButtonNegativeGrey
import week.on.a.plate.core.theme.ColorPanelGreen
import week.on.a.plate.core.theme.ColorPanelYellow
import week.on.a.plate.core.theme.ColorSubTextGrey
import week.on.a.plate.core.theme.ColorTextBlack
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.theme.bodyGrey
import week.on.a.plate.core.theme.titleLargeNonItalic
import week.on.a.plate.core.theme.titleMediumItalic


@Composable
fun TextInApp(
    text: String,
    modifier: Modifier = Modifier,
    maxLines: Int? = null,
    textStyle: TextStyle = Typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onBackground,
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
fun TextDisplayItalic(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    TextInApp(
        text,
        modifier,
        textStyle = Typography.displayMedium,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = textAlign
    )
}

@Composable
fun TextTitleLargeItalic(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    TextInApp(
        text,
        modifier,
        textStyle = Typography.titleLarge,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = textAlign
    )
}

@Composable
fun TextTitleLarge(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    TextInApp(
        text,
        modifier,
        textStyle = titleLargeNonItalic,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = textAlign
    )
}


@Composable
fun TextTitleItalic(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    TextInApp(
        text,
        modifier,
        textStyle = titleMediumItalic,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = textAlign
    )
}

@Composable
fun TextTitle(text: String, modifier: Modifier = Modifier, textAlign: TextAlign = TextAlign.Start, color: Color = MaterialTheme.colorScheme.onBackground,) {
    TextInApp(
        text,
        modifier,
        textStyle = Typography.titleMedium,
        color = color,
        textAlign = textAlign,
    )
}

@Composable
fun TextBodyDisActive(text: String, modifier: Modifier = Modifier) {
    TextInApp(
        text,
        modifier,
        textStyle = bodyGrey,
        color = ColorSubTextGrey,
        textAlign = TextAlign.Start
    )
}

@Composable
fun SubText(text: String, modifier: Modifier = Modifier, textAlign : TextAlign = TextAlign.Center) {
    TextInApp(
        text,
        modifier,
        textStyle = Typography.bodySmall,
        color = ColorSubTextGrey,
        textAlign = textAlign
    )
}

@Composable
fun TextSmall(text: String, modifier: Modifier = Modifier,
              color: Color = MaterialTheme.colorScheme.onBackground) {
    TextInApp(
        text,
        modifier,
        textStyle = Typography.bodySmall,
        color = color,
        textAlign = TextAlign.Start
    )
}

@Composable
fun TextBody(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int? = null,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    TextInApp(
        text,
        modifier,
        textStyle = Typography.bodyMedium,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines
    )
}

@Composable
fun TagBig(tag: RecipeTagView, isActive: Boolean, clickable: () -> Unit = {}) {
    TagBig(tag.tagName, if (isActive) ColorPanelGreen else ColorButtonNegativeGrey, clickable)
}

@Composable
fun CreateTagOrIngredient(name: String, eventCreate: () -> Unit)
{
    TagBig("+ Создать: " + name, ColorButtonNegativeGrey, eventCreate)
}
@Composable
fun TagBig(ingredientView: IngredientView, isActive: Boolean, clickable: () -> Unit = {}) {
    TagBig(ingredientView.name, if (isActive) ColorPanelYellow else ColorButtonNegativeGrey, clickable)
}

@Composable
fun TagBig(text: String, color: Color, clickable: () -> Unit = {}) {
    TextInApp(
        text, modifier = Modifier
            .clickable {
                clickable()
            }
            .background(
                color, RoundedCornerShape(30.dp)
            )
            .padding(horizontal = 24.dp, vertical = 6.dp), textStyle = Typography.bodyMedium,
        maxLines = 1, color = ColorTextBlack,
        textAlign = TextAlign.Start
    )
}

@Composable
fun TagSmall(tag: RecipeTagView) {
    val backg =
        ColorPanelGreen
    TagSmall(tag.tagName, backg)
}

@Composable
fun TagSmall(ingredientView: IngredientView) {
    val backg = ColorPanelYellow
    TagSmall(ingredientView.name, backg)
}

@Composable
private fun TagSmall(text: String, color: Color) {
    TextInApp(
        text, modifier = Modifier
            .padding(end = 6.dp, bottom = 6.dp)
            .background(
                color, RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 10.dp, vertical = 2.dp), textStyle = Typography.bodySmall,
        maxLines = 1, color = ColorTextBlack,
        textAlign = TextAlign.Start
    )
}


// todo: del
@Composable
fun TextInAppColored(
    text: String,
    colorBackground: Color,
    modifier: Modifier = Modifier,
    maxLines: Int? = null,
    textStyle: TextStyle = Typography.bodyMedium,
    color: Color = Typography.bodyMedium.color,
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

@Preview(showBackground = true)
@Composable
fun PreviewPlusButton() {
    WeekOnAPlateTheme {
        Column {
            TextInApp("Text 1")
            TextInAppColored("Text 2", ColorButtonGreen)
            TagSmall("Гречка", ColorPanelYellow)


            TextDisplayItalic("TextDisplayItalic")
            TextTitleLargeItalic("TextTitleLargeItalic")
            TextTitleLarge("TextTitleLarge")
            TextTitleItalic("TextTitleItalic")
            TextTitle("TextTitle")
            TextBodyDisActive("TextBodyDisActive")
            TextBody("TextBody")
            SubText("SubText")
        }
    }
}