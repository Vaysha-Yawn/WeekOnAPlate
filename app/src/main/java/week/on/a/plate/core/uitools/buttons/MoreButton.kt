package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.ui.theme.ColorButtonNegativeGrey
import week.on.a.plate.ui.theme.ColorPanelLightGrey
import week.on.a.plate.ui.theme.ColorStrokeGrey
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun MoreButton(actionEdit: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.more),
        contentDescription = "",
        modifier = Modifier
            .size(24.dp)
            .clickable { actionEdit() },
    )
}


@Composable
fun MoreButtonWithBackg(actionEdit: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.more),
        contentDescription = "",
        modifier = Modifier
            .background(ColorPanelLightGrey, RoundedCornerShape(5.dp))
            .size(24.dp)
            .clickable { actionEdit() },
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMoreButton() {
    WeekOnAPlateTheme {
        Column {
            MoreButton() {}
            MoreButtonWithBackg() {}
        }
    }
}