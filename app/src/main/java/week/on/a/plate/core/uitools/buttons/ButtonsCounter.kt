package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.ui.theme.ColorButton
import week.on.a.plate.ui.theme.ColorNav
import week.on.a.plate.ui.theme.ColorPlan
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun ButtonsCounter(
    value: State<Int>,
    plus:()->Unit,
    minus:()->Unit,
    ) {
    Row(horizontalArrangement = Arrangement.Absolute.Center, modifier = Modifier
        .background(
            Color.White, RoundedCornerShape(10.dp)
        )
        .padding(10.dp)) {
        ButtonText(
            "-", modifier = Modifier
                .clickable {
                    minus()
                }, colorBackground = ColorNav
        )
        Spacer(modifier = Modifier.width(10.dp))
        ButtonText(
            value.value.toString(), colorBackground = Color.White
        )
        Spacer(modifier = Modifier.width(10.dp))
        ButtonText(
            "+", modifier = Modifier
                .clickable {
                    plus()
                }, colorBackground = ColorNav
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewButtonsCounter() {
    val state = remember {
        mutableIntStateOf(0)
    }
    WeekOnAPlateTheme {
        ButtonsCounter(state, {}, {})
    }
}