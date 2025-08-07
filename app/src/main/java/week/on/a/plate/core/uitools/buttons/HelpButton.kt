package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.app.mainActivity.event.EmptyNavParams
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.screens.additional.tutorial.navigation.TutorialDestination
import week.on.a.plate.screens.additional.tutorial.state.TutorialEnum

@Composable
fun HelpButton(target: TutorialEnum, mainEvent: (MainEvent) -> Unit) {
    Icon(
        Icons.Rounded.Info,
        contentDescription = "Image",
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onBackground, CircleShape)
            .size(24.dp)
            .clickNoRipple {
                mainEvent(MainEvent.Navigate(TutorialDestination(target.name), EmptyNavParams))
            },
        tint = MaterialTheme.colorScheme.secondary
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHelpButton() {
    WeekOnAPlateTheme {
        HelpButton(TutorialEnum.Menu) {}
    }
}