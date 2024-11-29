package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.screens.filters.view.clickNoRipple

@Composable
fun ActionPlusButton(modifier:Modifier = Modifier, actionAdd: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.add),
        contentDescription = "",
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary, CircleShape)
            .padding(6.dp)
            .size(48.dp)
            .clickNoRipple(actionAdd),
    )
}

@Composable
fun PlusButtonCard(actionAdd: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.add),
        contentDescription = "",
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(5.dp))
            .size(36.dp)
            .clickNoRipple(actionAdd),
    )
}

@Composable
fun PlusButtonTitle(actionAdd: () -> Unit) {
    Image(
        painter = painterResource(id = R.drawable.add),
        contentDescription = "",
        modifier = Modifier
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(5.dp))
            .padding(2.dp)
            .size(24.dp)
            .clickNoRipple(actionAdd),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPlusButton() {
    WeekOnAPlateTheme {
        Column {
            ActionPlusButton() {}
            PlusButtonCard() {}
            PlusButtonTitle() {}
        }
    }
}