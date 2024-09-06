package week.on.a.plate.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    displayMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Italic,
        fontSize = 48.sp,
        lineHeight = 48.sp,
        color = ColorTextBlack
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Italic,
        fontSize = 32.sp,
        lineHeight = 32.sp,
        color = ColorTextBlack
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Normal,
        fontSize = 24.sp,
        lineHeight = 24.sp,
        color = ColorTextBlack
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp,
        color = ColorTextBlack
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        fontSize = 10.sp,
        lineHeight = 10.sp,
        color = ColorSubTextGrey
    ),
)

val titleMediumItalic = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Light,
    fontStyle = FontStyle.Italic,
    fontSize = 24.sp,
    lineHeight = 24.sp,
    color = ColorTextBlack
)

val titleLargeNonItalic = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Light,
    fontStyle = FontStyle.Normal,
    fontSize = 32.sp,
    lineHeight = 32.sp,
    color = ColorTextBlack
)

val bodyGrey = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontStyle = FontStyle.Normal,
    fontSize = 14.sp,
    lineHeight = 14.sp,
    color = ColorSubTextGrey
)