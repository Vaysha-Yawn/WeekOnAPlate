package week.on.a.plate.core.theme

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
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Italic,
        fontSize = 32.sp,
        lineHeight = 32.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Normal,
        fontSize = 24.sp,
        lineHeight = 24.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Italic,
        fontSize = 18.sp,
        lineHeight = 20.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        fontSize = 18.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontStyle = FontStyle.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
)

val bodyMediumSemiBold = TextStyle(
fontFamily = FontFamily.Default,
fontWeight = FontWeight.Medium,
fontStyle = FontStyle.Normal,
fontSize = 18.sp,
lineHeight = 20.sp,
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