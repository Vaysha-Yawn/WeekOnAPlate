package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextDisplayItalic
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.ui.theme.ColorButtonNegativeGrey
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.ui.theme.WeekOnAPlateTheme
import week.on.a.plate.ui.theme.paddingCommon

@Composable
fun ButtonsCounter(
    value: State<Int>,
    plus: () -> Unit,
    minus: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Absolute.Center, modifier = Modifier
            .background(
                MaterialTheme.colorScheme.tertiary, RoundedCornerShape(10.dp)
            )
            .padding(10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.remove),
            contentDescription = "",
            modifier = Modifier
                .background(ColorButtonNegativeGrey, CircleShape)
                .padding(12.dp)
                .size(24.dp)
                .clickable { minus() },
        )
        TextInApp(
            value.value.toString(),
            textStyle = Typography.displaySmall,
            modifier = Modifier.padding(horizontal = paddingCommon)
        )
        Image(
            painter = painterResource(id = R.drawable.plus),
            contentDescription = "",
            modifier = Modifier
                .background(ColorButtonNegativeGrey, CircleShape)
                .padding(12.dp)
                .size(24.dp)
                .clickable { plus() },
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
        Column {
            ButtonsCounter(state, {}, {})
        }

    }
}