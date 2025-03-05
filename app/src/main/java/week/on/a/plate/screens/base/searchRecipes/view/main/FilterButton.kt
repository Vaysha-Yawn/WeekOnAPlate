package week.on.a.plate.screens.base.searchRecipes.view.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.uitools.clickNoRipple

@Composable
fun FilterButton(filterCount: Int, actionFilter: () -> Unit) {
    Box(contentAlignment = Alignment.TopEnd) {
        Icon(
            painter = painterResource(id = R.drawable.sort),
            contentDescription = "Sort",
            modifier = Modifier
                .clickNoRipple(actionFilter)
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))
                .padding(6.dp),
        )
        IndicatorFilter(filterCount)
    }
}

@Composable
fun IndicatorFilter(filterCount: Int) {
    if (filterCount != 0) {
        TextInApp(
            text = filterCount.toString(),
            Modifier
                .offset(10.dp, (-8).dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .padding(horizontal = 8.dp, vertical = 3.dp),
            textStyle = Typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterButton() {
    WeekOnAPlateTheme {
        Column {
            FilterButton(0) {}
        }
    }
}