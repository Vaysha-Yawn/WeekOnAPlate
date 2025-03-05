package week.on.a.plate.dialogs.forSearchScreen.filtersMore.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TagSmall
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.core.utils.timeToString
import week.on.a.plate.dialogs.forSearchScreen.filtersMore.state.FiltersMoreUIState

@Composable
fun FilterMoreContent(state: FiltersMoreUIState) {
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp)
    ) {
        TextTitle(
            stringResource(R.string.more_filters),
            Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        FilterFavorites(state)
        Spacer(Modifier.height(24.dp))

        FilterCookTime(state)
    }
}

@Composable
private fun FilterCookTime(state: FiltersMoreUIState) {
    val listTime = remember {
        listOf(
            15,
            30,
            60,
            90,
            120,
        )
    }
    TextBody(stringResource(R.string.cook_time))
    Spacer(Modifier.height(24.dp))
    TagsListFilterTime(state.allTime, listTime)
    Spacer(Modifier.height(24.dp))
}

@Composable
private fun FilterFavorites(state: FiltersMoreUIState) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickNoRipple {
                state.favoriteIsChecked.value = !state.favoriteIsChecked.value
            }, verticalAlignment = Alignment.CenterVertically

    ) {
        Checkbox(
            checked = state.favoriteIsChecked.value,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary,
                checkmarkColor = MaterialTheme.colorScheme.onBackground
            ),
            onCheckedChange = {
                state.favoriteIsChecked.value = !state.favoriteIsChecked.value
            },
        )
        Spacer(Modifier.width(6.dp))
        TextBody(stringResource(R.string.only_favorites))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TagsListFilterTime(value: MutableIntState, listTime: List<Int>) {
    FlowRow() {
        listTime.forEach {
            val text = stringResource(R.string.under)+ " " +(it*60).timeToString(LocalContext.current)
            TagFilterTime(value.intValue, it, text) {
                if (value.intValue == it) {
                    value.intValue = 0
                } else {
                    value.intValue = it
                }
            }
        }
    }
}

@Composable
fun TagFilterTime(currentValue: Int, targetValue: Int, text: String, onClick: () -> Unit) {
    TagSmall(
        text,
        modifier = Modifier
            .clickNoRipple(onClick)
            .padding(end = 12.dp, bottom = 12.dp),
        color = if (currentValue == targetValue) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSortMoreContent() {
    WeekOnAPlateTheme {
        FilterMoreContent(FiltersMoreUIState())
    }
}