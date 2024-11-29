package week.on.a.plate.core.uitools.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.ColorButtonGreen
import week.on.a.plate.core.theme.ColorTextBlack
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.screens.filters.view.clickNoRipple

@Composable
fun DoneButton(
    text: String,
    modifier: Modifier = Modifier,
    click: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth().clickNoRipple(click)
            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(20.dp))
            .padding(10.dp)
            , horizontalArrangement = Arrangement.Center
    ) {
        TextBody(text = text, color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun DoneButtonSmall(
    text: String,
    modifier: Modifier = Modifier,
    click: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth().clickNoRipple(click)

            .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(20.dp))
            .padding(10.dp)
            , horizontalArrangement = Arrangement.Center
    ) {
        TextInApp(text = text, textStyle = Typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun CommonButton(
    text: String,
    modifier: Modifier = Modifier,
    image: Int? = null,
    click: () -> Unit
) {
    Row(
        modifier
            .fillMaxWidth().clickNoRipple(click)
            .background(
                MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp)
            )
            .padding(10.dp)
            ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier)
        TextInApp(
            text = text,
            textStyle = Typography.bodyMedium,
            color =  MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        if (image == null) {
            Spacer(modifier = Modifier)
        } else {
            Icon(
                painter = painterResource(id = image),
                contentDescription = "",
                modifier = Modifier.size(36.dp),
                tint = (if (isSystemInDarkTheme()) ColorTextBlack else MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}

@Composable
fun TextButton(
    text: String,
    modifier: Modifier = Modifier,
    image: Int? = null,
    click: () -> Unit
) {
    Row(
        modifier.clickNoRipple(click)
            .background(
                MaterialTheme.colorScheme.secondary, RoundedCornerShape(20.dp)
            )
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier)
        TextInApp(
            text = text,
            textStyle = Typography.bodyMedium,
            color =  MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
        if (image == null) {
            Spacer(modifier = Modifier)
        } else {
            Icon(
                painter = painterResource(id = image),
                contentDescription = "",
                modifier = Modifier.size(36.dp),
                tint = (if (isSystemInDarkTheme()) ColorTextBlack else MaterialTheme.colorScheme.onBackground)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDoneButton() {
    WeekOnAPlateTheme {
        Column {
            DoneButton("Готово") {}
            CommonButton("Нет") {}
        }
    }
}