package week.on.a.plate.screens.base.menu.presenter.view.week

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.TitleMenuS
import week.on.a.plate.core.uitools.buttons.PlusButtonTitle
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import week.on.a.plate.screens.base.menu.presenter.state.MenuUIState
import week.on.a.plate.screens.base.menu.presenter.view.week.positionsList.CardPosition

@Composable
fun WeekMenu(
    menuUIState: MenuUIState,
    onEvent: (event: Event) -> Unit,
    week: WeekView
) {
    LazyColumn(Modifier.padding(horizontal = 12.dp)) {
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Column {
                Row {
                    TextTitle(
                        text = stringResource(R.string.for_week),
                        Modifier.padding(start = 12.dp)
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    val context = LocalContext.current
                    PlusButtonTitle {
                        if (week.selectionView.positions.isEmpty() && week.selectionView.id == 0L) {
                            onEvent(MenuEvent.CreateWeekSelIdAndCreatePosition(context))
                        } else {
                            onEvent(MenuEvent.CreatePosition(week.selectionView.id, context))
                        }
                    }
                }
                Spacer(modifier = Modifier.size(12.dp))
                if (week.selectionView.positions.isNotEmpty()) {
                    for (pos in week.selectionView.positions) {
                        CardPosition(
                            position = pos,
                            menuUIState = menuUIState,
                            onEvent
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
        }

        items(items = week.days, key = { it.date }) { day ->
            HorizontalDivider(
                color = MaterialTheme.colorScheme.outline,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(12.dp))
            Column() {
                TextTitle(
                    text = day.getDyInWeekFull(LocalContext.current.resources.configuration.locales[0]) + ", " + day.date.dayOfMonth.toString(),
                    modifier = Modifier.padding(start = 12.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Column(Modifier.animateContentSize()) {
                    for (sel in day.selections) {
                        Column {
                            Spacer(modifier = Modifier.size(10.dp))
                            TitleMenuS(sel, Modifier.padding(start = 12.dp), onEvent)
                            Spacer(modifier = Modifier.size(10.dp))
                            sel.positions.forEach { pos ->
                                CardPosition(
                                    position = pos,
                                    menuUIState = menuUIState,
                                    onEvent
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SellTitleWeek(sel: SelectionView, onEvent: (event: Event) -> Unit) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))
    ) {
        SubText(
            text = sel.name,
            modifier = Modifier
                .weight(1f)
                .combinedClickable(
                    onClick = {},
                    onLongClick = {
                        onEvent(MenuEvent.EditOrDeleteSelection(sel, context))
                    }
                ),
        )
        PlusButtonTitle {
            onEvent(MenuEvent.CreatePosition(sel.id, context))
        }
    }
}