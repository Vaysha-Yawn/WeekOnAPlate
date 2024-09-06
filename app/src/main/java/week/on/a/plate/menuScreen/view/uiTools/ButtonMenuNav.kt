package week.on.a.plate.menuScreen.view.uiTools

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import week.on.a.plate.core.uitools.TextInAppColored
import week.on.a.plate.ui.theme.ColorBluePanel
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.ui.theme.WeekOnAPlateTheme


@Composable
fun ButtonMenuNav(itsDayMenu:Boolean, actionNav:()->Unit) {
    TextInAppColored(
        if (itsDayMenu) {
            "К меню на неделю ->"
        } else {
            "К меню на день ->"
        }, colorBackground = ColorBluePanel,
        modifier = Modifier.clickable(onClick = actionNav),
        textStyle = Typography.bodyMedium
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewButtonMenuNav() {
    WeekOnAPlateTheme {
        ButtonMenuNav(true){}
    }
}