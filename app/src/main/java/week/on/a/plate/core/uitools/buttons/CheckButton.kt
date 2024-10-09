package week.on.a.plate.core.uitools.buttons

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.ColorButtonGreen
import week.on.a.plate.core.theme.ColorDarkButtonGreen
import week.on.a.plate.core.theme.ColorStrokeBlue
import week.on.a.plate.core.theme.WeekOnAPlateTheme

@Composable
fun CheckButton(checked: State<Boolean>, actionCheck: () -> Unit) {
    RadioButton(
        selected = checked.value, onClick = {
            actionCheck()
        }, Modifier.size(24.dp).animateContentSize(), colors = RadioButtonDefaults.colors(
            selectedColor = ColorDarkButtonGreen,
            unselectedColor = Color.Gray,
            disabledSelectedColor = Color.Gray,
            disabledUnselectedColor = Color.Gray,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCheckButton() {
    val state = remember {
        mutableStateOf(true)
    }
    WeekOnAPlateTheme {
        CheckButton(state) {}
    }
}