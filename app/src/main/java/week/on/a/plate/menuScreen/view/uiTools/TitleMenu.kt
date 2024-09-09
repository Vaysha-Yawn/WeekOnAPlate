package week.on.a.plate.menuScreen.view.uiTools

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.core.uitools.buttons.PlusButtonCard
import week.on.a.plate.core.uitools.buttons.PlusButtonTitle
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun TitleMenu(name:String, actionAdd:()->Unit){
    Row(Modifier.padding(horizontal = 10.dp).padding(start = 20.dp), verticalAlignment = Alignment.CenterVertically) {
        TextTitle(text = name, modifier = Modifier.padding(end = 20.dp))
        PlusButtonTitle(actionAdd)
    }
}

@Composable
fun TitleMenuSmall(name:String, actionAdd:()->Unit){
    Row(Modifier.padding(horizontal = 10.dp).padding(start = 20.dp), verticalAlignment = Alignment.CenterVertically) {
        TextTitleItalic(text = name, modifier = Modifier.padding(end = 10.dp))
        PlusButtonTitle(actionAdd)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTitleMenu() {
    WeekOnAPlateTheme {
        Column {
            TitleMenu("Понедельник"){}
            TitleMenuSmall("Понедельник"){}
        }
    }
}