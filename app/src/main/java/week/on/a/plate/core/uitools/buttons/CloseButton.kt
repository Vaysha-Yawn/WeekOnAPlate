package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.ui.theme.ColorStrokeGrey
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun CloseButton(actionClose: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.close),
        contentDescription = "",
        modifier = Modifier
            .padding(12.dp)
            .size(24.dp)
            .clickable { actionClose() },
    )
}

@Composable
fun CloseOutlined(actionClose: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.close),
        contentDescription = "",
        modifier = Modifier
            .border(1.dp, ColorStrokeGrey, RoundedCornerShape(10.dp))
            .padding(12.dp)
            .size(24.dp)
            .clickable { actionClose() },
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCloseButton() {
    WeekOnAPlateTheme {
        Column {
            CloseButton {}
            CloseOutlined{}
        }
    }
}