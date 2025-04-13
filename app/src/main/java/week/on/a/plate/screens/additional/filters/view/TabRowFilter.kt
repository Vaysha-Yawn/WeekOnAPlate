package week.on.a.plate.screens.additional.filters.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBody

@Composable
fun TabRowFilter(
    activeFilterTabIndex: MutableIntState,
    selectedTagsSize: Int,
    selectedIngredientsSize: Int
) {
    val tabTitles = listOf(stringResource(R.string.tags), stringResource(R.string.ingredients))
    TabRow(
        selectedTabIndex = activeFilterTabIndex.intValue,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[activeFilterTabIndex.intValue]),
                height = 2.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TextBody(
                            text =
                            if (title == stringResource(R.string.tags)) selectedTagsSize.toString()
                            else selectedIngredientsSize.toString()
                        )
                        TextBody(text = title)
                    }
                },
                selected = activeFilterTabIndex.intValue == index,
                onClick = { activeFilterTabIndex.intValue = index }
            )
        }
    }
}