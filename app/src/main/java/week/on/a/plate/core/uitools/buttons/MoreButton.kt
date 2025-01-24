package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.clickNoRipple

@Composable
fun MoreButton(actionEdit: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.more),
        contentDescription = "More",
        modifier = Modifier
            .size(24.dp)
            .clickNoRipple(actionEdit),
    )
}

@Composable
fun MoreButtonWithBackg(actionEdit: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.more),
        contentDescription = "More",
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
            .size(36.dp)
            .clickNoRipple(actionEdit),
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