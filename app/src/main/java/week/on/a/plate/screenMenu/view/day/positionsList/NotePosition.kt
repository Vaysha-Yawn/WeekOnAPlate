package week.on.a.plate.screenMenu.view.day.positionsList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.MoreButton
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screenMenu.event.MenuEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotePosition(
    note: Position.PositionNoteView,
    onEvent: (event: MenuEvent) -> Unit,
    rowScope: RowScope
) {
    with(rowScope) {
        Row(
            Modifier
                .weight(3f)
                .combinedClickable(
                    onClick = { onEvent(MenuEvent.EditOtherPosition(note)) },
                    onLongClick =
                    { onEvent(MenuEvent.SwitchEditMode) },
                )
                .padding(vertical = 5.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(id = R.drawable.note),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp),
            )
            Spacer(modifier = Modifier.width(12.dp))
            TextBody(
                note.note,
                modifier = Modifier,
            )
        }
        MoreButton {
            onEvent(MenuEvent.EditPositionMore(note))
        }
        Spacer(modifier = Modifier.width(12.dp))
    }
}