package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import week.on.a.plate.core.theme.ColorButtonNegativeGrey
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.uitools.clickNoRipple

@Composable
fun ButtonsCounter(
    value: State<Int>,
    minus: () -> Unit,
    plus: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Absolute.Center, modifier = Modifier
            .background(
                MaterialTheme.colorScheme.background, RoundedCornerShape(10.dp)
            )
            .padding(10.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.remove),
            contentDescription = "Minus portions",
            modifier = Modifier
                .background(ColorButtonNegativeGrey, CircleShape)
                .padding(12.dp)
                .size(24.dp)
                .clickNoRipple(minus),
        )
        TextInApp(
            value.value.toString(),
            textStyle = Typography.displaySmall,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.plus),
            contentDescription = "Plus portions",
            modifier = Modifier
                .background(ColorButtonNegativeGrey, CircleShape)
                .padding(12.dp)
                .size(24.dp)
                .clickNoRipple (plus) ,
        )
    }
}

@Composable
fun ButtonsCounterSmall(
    value: State<Int>,
    minus: () -> Unit,
    plus: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.Absolute.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(10.dp)).padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.remove),
            contentDescription = "Minus portions",
            modifier = Modifier
                .background(ColorButtonNegativeGrey, CircleShape)
                .padding(6.dp)
                .size(20.dp)
                .clickNoRipple(minus),
        )
        TextInApp(
            value.value.toString(),
            textStyle = Typography.titleMedium,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.plus),
            contentDescription = "Plus portions",
            modifier = Modifier
                .background(ColorButtonNegativeGrey, CircleShape)
                .padding(6.dp)
                .size(20.dp)
                .clickNoRipple (plus),
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