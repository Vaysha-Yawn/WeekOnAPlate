package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.ui.theme.ColorButton
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun PlusButton(actionAdd: () -> Unit) {
    Image(
        painter = rememberVectorPainter(Icons.Rounded.Add),
        contentDescription = "",
        modifier = Modifier
            .clickable { actionAdd() }
            .background(ColorButton, RoundedCornerShape(5.dp))
            .padding(2.dp),
    )
}

@Composable
fun PlusButtonSmall(actionAdd: () -> Unit) {
        Image(
            painter = rememberVectorPainter(Icons.Rounded.Add),
            contentDescription = "",
            modifier = Modifier
                .clickable { actionAdd() }
                .width(12.dp)
                .height(12.dp)
                .background(ColorButton, RoundedCornerShape(4.dp))
        )
}

@Preview(showBackground = true)
@Composable
fun PreviewPlusButton() {
    WeekOnAPlateTheme {
        Column {
            PlusButton() {}
            PlusButtonSmall() {}
        }
    }
}