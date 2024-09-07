package week.on.a.plate.menuScreen.view.day

import android.icu.util.LocaleData
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.menuScreen.logic.MenuEvent
import week.on.a.plate.menuScreen.logic.MenuIUState
import week.on.a.plate.menuScreen.view.recipeBlock.BlockSelection
import week.on.a.plate.menuScreen.view.recipeBlock.BlockSelectionSmall
import week.on.a.plate.ui.theme.Typography
import java.time.LocalDate

@Composable
fun WeekView(
    menuIUState: MenuIUState,
    onEvent: (event: MenuEvent) -> Unit
) {
    val week = menuIUState.week
    LazyColumn {
        item {
            BlockSelection(
                selection = week.selection,
                LocalDate.parse("0"),
                menuIUState = menuIUState,
                onEvent
            )
        }
        for (day in week.days) {
            item {
                TextInApp(
                    text = day.dayInWeek.fullName,
                    textStyle = Typography.titleLarge,
                    modifier = Modifier.padding(start = 10.dp)
                )
                Spacer(Modifier.height(10.dp))
            }
            items(day.selections.size) { index ->
                BlockSelectionSmall(
                    selection = day.selections[index],
                    day.date,
                    menuIUState = menuIUState,
                    onEvent
                )
            }
            item {
                Spacer(Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun DayView(day: DayView, menuIUState: MenuIUState, onEvent: (event: MenuEvent) -> Unit) {
    LazyColumn {
        items(day.selections.size) { index ->
            BlockSelection(
                selection = day.selections[index],day.date,
                menuIUState = menuIUState,
                onEvent
            )
        }
    }
}