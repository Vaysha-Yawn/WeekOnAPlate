package week.on.a.plate.screens.menu.view.week.positionsList

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.MoreButton
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.core.wrapperDatePicker.event.WrapperDatePickerEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotePosition(
    note: Position.PositionNoteView,
    onEvent: (event: Event) -> Unit,
    rowScope: RowScope
) {
    val context = LocalContext.current
    with(rowScope) {
        Row(
            Modifier
                .weight(3f)
                .combinedClickable(
                    onClick = { onEvent(week.on.a.plate.screens.menu.event.MenuEvent.EditOtherPosition(note, context)) },
                    onLongClick =
                    { onEvent(WrapperDatePickerEvent.SwitchEditMode) },
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
            onEvent(week.on.a.plate.screens.menu.event.MenuEvent.EditPositionMore(note, context))
        }
        Spacer(modifier = Modifier.width(12.dp))
    }
}