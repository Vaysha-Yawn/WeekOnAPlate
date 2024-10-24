package week.on.a.plate.core.uitools

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.ColorSubTextGrey
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.buttons.PlusButtonTitle
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.screens.menu.event.MenuEvent
import java.time.LocalDateTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TitleMenu(selection: SelectionView, modifier: Modifier, onEvent: (event: Event) -> Unit) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextTitle(
            text = selection.name,
            modifier = Modifier
                .padding(end = 20.dp)
                .combinedClickable(
                    onClick = {},
                    onLongClick = {
                        onEvent(MenuEvent.EditOrDeleteSelection(selection))
                    }
                ),
        )
        PlusButtonTitle() {
            if (selection.id == 0L) {
                onEvent(MenuEvent.CreateFirstNonPosedPosition(selection.dateTime.toLocalDate(), selection.name))
            } else {
                onEvent(MenuEvent.CreatePosition(selection.id))
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TitleMenuS(selection: SelectionView, modifier: Modifier, onEvent: (event: Event) -> Unit) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextTitle(
            text = selection.name,
            modifier = Modifier
                .padding(end = 20.dp)
                .combinedClickable(
                    onClick = {},
                    onLongClick = {
                        onEvent(week.on.a.plate.screens.menu.event.MenuEvent.EditOrDeleteSelection(selection))
                    }
                ),
            color = ColorSubTextGrey
        )
        PlusButtonTitle() {
            if (selection.id == 0L) {
                onEvent(week.on.a.plate.screens.menu.event.MenuEvent.CreateFirstNonPosedPosition(selection.dateTime.toLocalDate(), selection.name))
            } else {
                onEvent(week.on.a.plate.screens.menu.event.MenuEvent.CreatePosition(selection.id))
            }
        }
    }
}

@Composable
fun TitleMenuSmall(name: String, actionAdd: () -> Unit) {
    Row(Modifier, verticalAlignment = Alignment.CenterVertically) {
        TextBody(text = name, modifier = Modifier.padding(end = 24.dp))
        PlusButtonTitle(actionAdd)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTitleMenu() {
    WeekOnAPlateTheme {
        Column {
            TitleMenu(SelectionView(0L, "Понедельник", LocalDateTime.now(), 0,
                false,mutableListOf()), Modifier) {}
            TitleMenuSmall("Понедельник") {}
        }
    }
}