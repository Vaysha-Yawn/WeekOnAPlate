package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorStrokeGrey
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.screens.filters.view.clickNoRipple

@Composable
fun BackButton(actionBack: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.back),
        contentDescription = "",
        modifier = Modifier
            .padding(12.dp)
            .size(24.dp)
            .clickNoRipple(actionBack),
    )
}

@Composable
fun BackButtonOutlined(actionBack: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.back),
        contentDescription = "",
        modifier = Modifier
            .clickNoRipple(actionBack)
            .border(1.dp, ColorStrokeGrey, RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))
            .padding(12.dp)
            .size(24.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNavBackButton() {
    WeekOnAPlateTheme {
        Column {
            BackButton {}
            BackButtonOutlined{}
        }
    }
}