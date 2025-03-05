package week.on.a.plate.dialogs.forSearchScreen.sortMore.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.dialogs.forSearchScreen.sortMore.event.SortMoreEvent
import week.on.a.plate.core.uitools.clickNoRipple

@Composable
fun SortMoreContent( onEvent:(SortMoreEvent) -> Unit){
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp)) {
        TextTitle(stringResource(R.string.sort), Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        val listSort = listOf(Triple(stringResource(R.string.a_z_alphabetically), R.drawable.sort_by_alpha, SortMoreEvent.AlphabetNormal),
            Triple(stringResource(R.string.z_a_reverse_alphabetical),  R.drawable.sort_by_alpha, SortMoreEvent.AlphabetRevers),
            Triple(stringResource(R.string.from_old_to_new),  R.drawable.calendar, SortMoreEvent.DateFromOldToNew),
            Triple(stringResource(R.string.from_new_to_old),  R.drawable.calendar, SortMoreEvent.DateFromNewToOld),
            Triple(stringResource(R.string.random_order),  R.drawable.random, SortMoreEvent.Random))

        for ((text, res, event) in listSort){
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickNoRipple {
                        onEvent(event)
                    }
                    .padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(painterResource(res), "", tint = MaterialTheme.colorScheme.onBackground)
                Spacer(Modifier.width(24.dp))
                TextBody(text)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSortMoreContent(){
    WeekOnAPlateTheme {
        SortMoreContent(){}
    }
}