package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.ui.theme.ColorButton
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun NavBackButton(actionBack: () -> Unit) {
    Image(
        painter = rememberVectorPainter(Icons.AutoMirrored.Rounded.ArrowBack),
        contentDescription = "",
        modifier = Modifier
            .clickable { actionBack() }
            .background(ColorButton, RoundedCornerShape(10.dp))
            .padding(5.dp),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNavBackButton() {
    WeekOnAPlateTheme {
        NavBackButton {}
    }
}